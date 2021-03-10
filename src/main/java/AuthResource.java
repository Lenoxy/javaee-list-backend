import entity.User;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Named
@Path("/auth")
public class AuthResource{
    @PersistenceContext
    EntityManager entityManager; //= Persistence.createEntityManagerFactory("list-db").createEntityManager();

    // TODO Modify REST request types, currently GET for simplicity

    @GET
    @Path("/insert")
    @Transactional
    public String insert(){
        User user = new User("autogen", "testuser");
        entityManager.persist(user);
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
