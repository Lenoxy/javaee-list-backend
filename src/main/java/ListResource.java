//import interceptor.Protected;

import interceptor.Protected;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/list")
public class ListResource{
    @GET
    @Path("")
    @Protected
    public String getProperties(){
        return "Hello list";
    }

}
