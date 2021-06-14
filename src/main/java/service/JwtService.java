package service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dto.UserDto;

import javax.ejb.EJB;

@EJB
public class JwtService{
    private final String ISSUER = "list-backend";
    // This is a test project and should never be used productively!
    private final Algorithm ALGORITHM = Algorithm.HMAC256("very_secret");

    private final JWTVerifier verifier = JWT.require(ALGORITHM)
            .withIssuer(ISSUER)
            .withClaimPresence("user")
            .withClaimPresence("id")
            .build();

    public JwtService(){
    }

    public String createJwt(UserDto userDto){
        return JWT.create()
                .withIssuer(ISSUER)
                .withClaim("user", userDto.getUsername())
                .withClaim("id", userDto.getId())
                .sign(ALGORITHM);
    }

    public boolean isJwtValid(String jwt){
        try{
            verifier.verify(jwt);
            return true;
        }catch(JWTVerificationException e){
            return false;
        }
    }

    public String getUsername(String jwt){
        try{
            DecodedJWT decodedJWT = verifier.verify(jwt);
            return decodedJWT.getClaim("user").asString();
        }catch(JWTVerificationException e){
            e.printStackTrace();
            return null;
        }
    }

    public Integer getId(String jwt){
        try{
            DecodedJWT decodedJWT = verifier.verify(jwt);
            return decodedJWT.getClaim("id").asInt();
        }catch(JWTVerificationException e){
            e.printStackTrace();
            return null;
        }
    }
}
