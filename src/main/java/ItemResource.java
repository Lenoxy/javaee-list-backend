import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class ItemResource{
    @GET
    @Path("")
    public String getItem(){
        return "Hello item";
    }
}
