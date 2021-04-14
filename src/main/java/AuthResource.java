import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import dto.UserDto;
import entity.ItemEntity;
import entity.ListEntity;
import entity.UserEntity;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Named
@Path("/auth")
public class AuthResource{
    @Inject
    Database database;


    @Path("/login")
    @PUT
    public Response login(UserDto userDto){
        if(userDto.isInvalid()){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Query q = database.entityManager.createQuery("SELECT u FROM UserEntity AS u WHERE u.username = :username");
        q.setParameter("username", userDto.getUsername());
        UserEntity user = (UserEntity) q.getSingleResult();

        if(
                userDto.getUsername().equals(user.getUsername()) &&
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
    @Consumes("application/json")
    public Response register(UserDto registerUserDto){
        if(registerUserDto.isInvalid()){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }


        return Response.status(Response.Status.OK).build();
    }


    @GET
    @Path("/insert")
    @Transactional
    public String insertGenerated(){
        UserEntity userEntity = new UserEntity();

        userEntity.setUsername("autogen-" + LocalDateTime.now());
        userEntity.setPasswordSHA256("testpass"); // Should be a SHA256 String

        ListEntity listEntity = new ListEntity();
        listEntity.setTitle("autogen-" + LocalDateTime.now());

        listEntity.addItem(new ItemEntity("Apples"));
        listEntity.addItem(new ItemEntity("Grapes"));

        userEntity.addListEntity(listEntity);

        database.entityManager.persist(userEntity);
        return "set";
    }

    @POST
    @Path("/insert")
    @Transactional
    @Consumes("application/json")
    public String insertCustom(UserEntity userEntity){
        database.entityManager.persist(userEntity);
        return "done";
    }

    @GET
    @Path("/select/{id}")
    @Produces("application/json")
    public UserDto selectById(@PathParam("id") int id){
        UserEntity userEntity = database.entityManager.getReference(UserEntity.class, id);
        return userEntity.toUserDto();
    }

    @GET
    @Path("/select")
    @Produces("application/json")
    public List<UserDto> select(){
        List<UserEntity> resultList = (List<UserEntity>) database.entityManager.createQuery("SELECT u FROM UserEntity AS u").getResultList();
        return resultList.stream().map(UserEntity::toUserDto).collect(Collectors.toList());
    }

}
