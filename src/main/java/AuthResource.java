import entity.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/auth")
public class AuthResource{
    @GET
    @Path("")
    public String getAuth(){


        return "Hello auth";
    }

}
