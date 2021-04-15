import dto.UserDto;
import entity.UserEntity;
import repository.UserRepository;
import service.DatabaseService;
import service.JWTService;

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

    //TODO: Remove
    @Inject
    DatabaseService database;


    @Inject
    JWTService jwtService;

    @Inject
    UserRepository userRepository;

    @Path("/login")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(UserDto userDto){
        if(!userDto.isValid()){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        userDto = userRepository.get(userDto);
        if(userDto != null){
            return Response
                    .status(Response.Status.OK)
                    .entity(jwtService.createJwt(userDto)).build();
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
        if(!userDto.isValid() || userDto.getLists() != null){
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

    //TODO: Make this an interceptor?
    @Path("verify")
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    public Response verify(String jwt){
        if(jwtService.isJwtValid(jwt)){
            return Response.status(Response.Status.ACCEPTED).build();
        }else{
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
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
