package service;

import dto.a;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JWTServiceTest{

    JWTService sut = new JWTService();

    @Test
    void createJwt(){
        String jwt = sut.createJwt(a.UserDtoBuilder().withUsername("user").build());

        assertThat(jwt).isEqualTo("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJsaXN0LWJhY2tlbmQiLCJ1c2VyIjoidXNlciJ9.1dozPe8laiqDo1GWBEZJwUxqkYrSI8LUpdSI8RwJRkQ");
    }

    @Test
    void isJwtValidHappyCase(){
        String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJsaXN0LWJhY2tlbmQiLCJ1c2VyIjoidXNlciJ9.1dozPe8laiqDo1GWBEZJwUxqkYrSI8LUpdSI8RwJRkQ";

        boolean isValid = sut.isJwtValid(jwt);

        assertThat(isValid).isTrue();

    }

    @Test
    void isJwtInvalid(){
        String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJsaXN0LWJhY2tlzmQiLCJ1c2VyIjoidXNlciJ9.1dozPe8laiqDo1GWBEZJwUxqkYrSI8LUpdSI8RwJRkQ";

        boolean isValid = sut.isJwtValid(jwt);

        assertThat(isValid).isFalse();

    }

    @Test
    void getValidUser(){
        String jwt = sut.createJwt(a.UserDtoBuilder().withUsername("user").build());

        String username = sut.getUsername(jwt);

        assertThat(username).isEqualTo("user");
    }
}
