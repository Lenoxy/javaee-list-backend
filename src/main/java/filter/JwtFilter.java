package filter;

import exception.BearerInvalidException;
import exception.BearerMissingException;
import exception.UserClaimMissingException;
import service.Jwt;
import service.JwtService;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;

@Provider
public class JwtFilter implements ContainerRequestFilter{
    @Inject
    JwtService jwtService;

    @Context
    private ResourceInfo resourceInfo;

    public void filter(ContainerRequestContext ctx){
        Method resourceMethod = resourceInfo.getResourceMethod();
        if(resourceMethod.isAnnotationPresent(RequiresLogin.class)){
            String jwtString = ctx.getHeaderString(HttpHeaders.AUTHORIZATION);

            if(jwtString == null){
                throw new BearerMissingException("Bearer is missing");
            }

            if(! jwtService.isJwtValid(jwtString)){
                throw new BearerInvalidException("The JWT is invalid");
            }

            // Not necessary but cool
            if(jwtService.decode(jwtString).getUser() == null){
                throw new UserClaimMissingException("The user claim is missing");
            }
        }
    }
}
