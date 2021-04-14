import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import dto.UserDto;
import entity.UserEntity;
import service.DatabaseService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Named
@Path("/auth")
public class UserResource{
    @Inject
    DatabaseService database;

    @Path("/login")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(UserDto userDto){
        if(userDto.isInvalid()){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Query q = database.entityManager.createQuery("SELECT u FROM UserEntity AS u WHERE u.username = :username");
        q.setParameter("username", userDto.getUsername());

        // TODO: Throws if no user is found
        UserEntity user = (UserEntity) q.getSingleResult();

        if(userDto.getUsername().equals(user.getUsername()) &&
                userDto.getPasswordSHA256().equals(user.getPasswordSHA256())
        ){
            Algorithm algorithm = Algorithm.HMAC256("secret"); // TODO
            return Response
                    .status(Response.Status.OK)
                    .entity(
                            JWT.create()
                                    .withIssuer("list-backend")
                                    .withClaim("username", user.getUsername())
                                    .sign(algorithm)
                    ).build();
        }else{
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @Path("/register")
    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response register(UserDto userDto){
        // Not very clean implementation... TODO: Revisit
        if(userDto.isInvalid() || userDto.getLists() != null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        UserEntity userEntity = userDto.toUserEntity();

        database.entityManager.persist(userEntity);

        // User is logged in automatically
        return Response
                .status(Response.Status.OK)
                .entity(login(userDto).getEntity())
                .build();
    }

    @GET
    @Path("/select/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserDto selectById(@PathParam("id") int id){
        UserEntity userEntity = database.entityManager.getReference(UserEntity.class, id);
        return userEntity.toUserDto();
    }

    @GET
    @Path("/select")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDto> selectAll(){
        List<UserEntity> resultList = (List<UserEntity>) database.entityManager.createQuery("SELECT u FROM UserEntity AS u").getResultList();
        return resultList.stream().map(UserEntity::toUserDto).collect(Collectors.toList());
    }

}
