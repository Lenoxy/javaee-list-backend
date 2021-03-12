import entity.User;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import java.util.List;
import java.util.stream.Collectors;

@Named
@Path("/auth")
public class AuthResource{
    @Inject
    Database database;

    @GET
    @Path("/insert")
    @Transactional
    public String insert(){
        User user = new User("autogen", "testuser");
        database.entityManager.persist(user);
        return "set";
    }

    @POST
    @Path("/insert")
    @Transactional
    public String insert(
            @FormParam("username") String username,
            @FormParam("password") String password
    ){
        User user = new User(username, password);
        database.entityManager.persist(user);
        return "done";
    }

    @GET
    @Path("/select/{id}")
    public String selectById(@PathParam("id") int id){
        User u = database.entityManager.getReference(User.class, id);
        return u.toString();
    }

    @GET
    @Path("/select")
    public String select(){
        List<User> userList = database.entityManager.createQuery("SELECT u FROM User AS u").getResultList();
        return userList
                .parallelStream()
                .map(User::toString)
                .collect(Collectors.toList())
                .toString();

    }

}
