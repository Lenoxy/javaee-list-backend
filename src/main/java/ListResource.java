import dto.ListDto;
import entity.ListEntity;
import service.DatabaseService;
import service.JWTService;

import javax.inject.Inject;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/list")
public class ListResource{

    @Inject
    JWTService jwtService;

    @Inject
    DatabaseService databaseService;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ListDto> getListsForUser(@HeaderParam(HttpHeaders.AUTHORIZATION) String jwt){
        String username = jwtService.getUserName(jwt);

        Query query = databaseService.getEM().createQuery("SELECT l FROM ListEntity AS l WHERE l.owner.username = :username");
        query.setParameter("username", username);
        List<ListEntity> listEntities = (List<ListEntity>) query.getResultList();
        return listEntities.stream().map(ListEntity::toListDto).collect(Collectors.toList());


    }

}
