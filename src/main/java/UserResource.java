import dto.UserDto;
import repository.UserRepository;
import service.JWTService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Named
@Path("/auth")
public class UserResource{

    @Inject
    private JWTService jwtService;

    @Inject
    private UserRepository userRepository;

    @Path("/login")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(UserDto userDto){
        if(! userDto.isValid()){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if(isCorrectCredentials(userDto)){
            return Response
                    .status(Response.Status.OK)
                    .entity(jwtService.createJwt(userDto))
                    .build();
        }else{
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private boolean isCorrectCredentials(UserDto userDto){
        UserDto responseUserDto = userRepository.getByUsernameAndPassword(userDto.getUsername(), userDto.getPasswordSHA256()).toUserDto();
        return responseUserDto != null;
    }

    @Path("/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response register(UserDto userDto){
        if(! userDto.isValid() || userDto.getLists() != null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        userRepository.add(userDto.toUserEntity());

        // User is logged in automatically
        return Response
                .status(Response.Status.OK)
                .entity(jwtService.createJwt(userDto))
                .build();
    }
}
