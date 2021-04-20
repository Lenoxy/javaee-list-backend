import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/list")
public class ListResource{
    @GET
    @Path("")
    public String getProperties(String jwt){
        return "Hello list";
    }

}
