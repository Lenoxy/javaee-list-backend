import entity.User;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Named
@Path("/auth")
public class AuthResource{
    //@Inject
    EntityManager entityManager = Persistence.createEntityManagerFactory("list-db").createEntityManager();

    // TODO Modify REST request types, currently GET for simplicity

    @GET
    @Path("/insert")
    public String insert(){
        User user = new User(1, "autogen", "testuser");
        new UserService().savePerson(user);
//        return "insert succeeded";
//        entityManager.createNativeQuery("INSERT INTO list.public.listuser (id, plainpassword, username) VALUES (1,'testuser', 'autogen')");
        return "set";
    }

    @GET
    @Path("/select/{id}")
    public String selectById(@PathParam("id") int id){
        User u = entityManager.getReference(User.class, id);
        return u.getUsername();
    }

    @GET
    @Path("/select")
    public String select(){
        List<User> userList = entityManager.createQuery("SELECT u FROM User AS u").getResultList();
        return userList.toString();
    }

}
