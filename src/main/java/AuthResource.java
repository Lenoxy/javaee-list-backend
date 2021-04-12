import dto.UserDto;
import entity.ItemEntity;
import entity.ListEntity;
import entity.UserEntity;

import javax.inject.Inject;
import javax.inject.Named;
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

    @PUT
    public String login(){
        return null;
    }

    @Path("/register")
    @POST
    @Consumes("application/json")
    public Response register(UserDto registerUserDto){
        if(!registerUserDto.isValid()){
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
        userEntity.setPlainPassword("testpass");

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
    public String selectById(@PathParam("id") int id){
        UserEntity u = database.entityManager.getReference(UserEntity.class, id);

        return u.toString();
    }

    @GET
    @Path("/select")
    @Produces("application/json")
    public List<UserDto> select(){
        // Mapper -> Mapstruct oder von Hand
        List<UserEntity> resultList = (List<UserEntity>) database.entityManager.createQuery("SELECT u FROM UserEntity AS u").getResultList();
        return resultList.stream().map(Mapper::toUserDto).collect(Collectors.toList());
    }

}
