package interceptor;

import service.JWTService;

import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
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
        if(! ctx.getUriInfo().getPath().contains("auth")){
            System.out.println(ctx.getHeaderString(HttpHeaders.AUTHORIZATION));
            String jwt = ctx.getHeaderString(HttpHeaders.AUTHORIZATION);

            if(jwt == null){
                throw new NotAuthorizedException("Bearer");
            }

            if(! jwtService.isJwtValid(jwt)){
                throw new NotAuthorizedException("The JWT is invalid");
            }

            // Not necessary but cool
            if(jwtService.getUser(jwt) == null){
                throw new NotAuthorizedException("The user claim is missing");
            }
        }
    }
}
