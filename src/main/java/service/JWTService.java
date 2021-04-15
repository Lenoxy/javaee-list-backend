package service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import entity.UserEntity;

import javax.ejb.EJB;
import java.util.HashMap;
import java.util.Map;

@EJB
public class JWTService{
    private final String ISSUER = "list-backend";
    private final Algorithm ALGORITHM = Algorithm.HMAC256("very_secret"); // TODO

    private final JWTVerifier verifier = JWT.require(ALGORITHM)
            .withIssuer(ISSUER)
            .withClaimPresence("user")
            .build();


    public String createJwt(UserEntity userEntity){
        System.out.println("userEntity.getUsername() = " + userEntity.getUsername());
        return JWT.create()
                .withIssuer(ISSUER)
                .withClaim("user", userEntity.getUsername())
                .sign(ALGORITHM);
    }

    public boolean isJwtValid(String jwt){
        try{
            DecodedJWT decodedJWT = verifier.verify(jwt);
            return true;
        }catch(JWTVerificationException e){
            e.printStackTrace();
            return false;
        }
    }
}
