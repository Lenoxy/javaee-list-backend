import com.google.gson.Gson;
import entity.ItemEntity;
import entity.ListEntity;
import entity.UserEntity;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Named
@Path("/auth")
public class AuthResource {
    @Inject
    Database database;

    @GET
    @Path("/insert")
    @Transactional
    public String insertGenerated() {
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
    public String insertCustom(
            @FormParam("username") String username,
            @FormParam("password") String password
    ) {
        UserEntity userEntity = new UserEntity(username, password, null);
        database.entityManager.persist(userEntity);
        return "done";
    }

    @GET
    @Path("/select/{id}")
    public String selectById(@PathParam("id") int id) {
        UserEntity u = database.entityManager.getReference(UserEntity.class, id);

        return u.toString();
    }

    @GET
    @Path("/select")
    public String select() {
        List<UserEntity> userEntityList = database.entityManager.createQuery("SELECT u FROM UserEntity AS u").getResultList();
        return new Gson().toJson(userEntityList);

    }

}
