import entity.User;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Named
@Path("/auth")
public class AuthResource{
    @Inject
    Database database;

    // TODO Modify REST request types, currently GET for simplicity

    @GET
    @Path("/insert")
    public String insert(){
        User u = new User("autogen","testuser");
        database.entityManager.persist(u);
        return "insert succeeded";
    }

    @GET
    @Path("/select/{id}")
    public String selectById(@PathParam("id") int id){
        User u = database.entityManager.getReference(User.class, id);
        return u.getUsername();
    }

}
