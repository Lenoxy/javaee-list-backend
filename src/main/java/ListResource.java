import dto.ListDto;
import entity.ListEntity;
import filter.RequiresLogin;
import repository.ListRepository;
import service.JwtService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/list")
public class ListResource{

    @Inject
    JwtService jwtService;

    @Inject
    ListRepository listRepository;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @RequiresLogin
    @Transactional
    public List<ListDto> getListsForUser(@HeaderParam(HttpHeaders.AUTHORIZATION) String jwt){
        return listRepository
                .getListByUserId(jwtService.decode(jwt).getId())
                .stream()
                .map(ListEntity::toListDto)
                .collect(Collectors.toList());
    }

    @POST
    @Path("")
    @Consumes(MediaType.TEXT_PLAIN)
    @RequiresLogin
    @Transactional
    public void addList(
            String title,
            @HeaderParam(HttpHeaders.AUTHORIZATION) String jwt
    ){
        listRepository.addList(jwtService.decode(jwt).getId(), title);

    }

}
