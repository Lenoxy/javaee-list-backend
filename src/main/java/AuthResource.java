import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Named
@Path("/auth")
public class AuthResource{
    @Inject
    Database database;

    @GET
    @Path("")
    public String getAuth(){
        return "Hello auth";
    }

    @Path("/test")
    public String test(){
        return database.get();
    }

}
