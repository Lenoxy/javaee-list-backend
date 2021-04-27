package filter;

import exception.BearerInvalidException;
import exception.BearerMissingException;
import exception.UserClaimMissingException;
import service.JWTService;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class JwtFilter implements ContainerRequestFilter{
    @Inject
    JWTService jwtService;


    public void filter(ContainerRequestContext ctx){
        // Let login and register requests pass through
        // TODO Extremly!!! insecure
        if(! ctx.getUriInfo().getPath().contains("auth")){
            String jwt = ctx.getHeaderString(HttpHeaders.AUTHORIZATION);

            if(jwt == null){
                throw new BearerMissingException("Bearer is missing");
            }

            if(! jwtService.isJwtValid(jwt)){
                throw new BearerInvalidException("The JWT is invalid");
            }

            // Not necessary but cool
            if(jwtService.getUserName(jwt) == null){
                throw new UserClaimMissingException("The user claim is missing");
            }
        }
    }
}
