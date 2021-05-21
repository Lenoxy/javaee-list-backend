package service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dto.UserDto;

import javax.ejb.EJB;

@EJB
public class JWTService{
    private final String ISSUER = "list-backend";
    private final Algorithm ALGORITHM = Algorithm.HMAC256("very_secret"); // TODO

    private final JWTVerifier verifier = JWT.require(ALGORITHM)
            .withIssuer(ISSUER)
            .withClaimPresence("user")
            .build();

    public JWTService(){
    }

    public String createJwt(UserDto userDto){
        return JWT.create()
                .withIssuer(ISSUER)
                .withClaim("user", userDto.getUsername())
                .sign(ALGORITHM);
    }

    public boolean isJwtValid(String jwt){
        try{
            DecodedJWT decodedJWT = verifier.verify(jwt);
            return true;
        }catch(JWTVerificationException e){
            return false;
        }
    }

    public String getUserName(String jwt){
        try{
            DecodedJWT decodedJWT = verifier.verify(jwt);
            return decodedJWT.getClaim("user").asString();
        }catch(JWTVerificationException e){
            e.printStackTrace();
            return null;
        }
    }
}
