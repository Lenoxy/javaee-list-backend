package service;

import dto.a;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtServiceTest{

    JwtService sut = new JwtService();

    @Test
    void createJwt(){
        String jwt = sut.createJwt(a.UserDtoBuilder().withUsername("user").withId(2).build());

        assertThat(jwt).isEqualTo("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJsaXN0LWJhY2tlbmQiLCJpZCI6MiwidXNlciI6InVzZXIifQ.CDMkMXAZIltDlYT-s7nU2reMXW6ytIwfBz4R8bGsQAk");
    }

    @Test
    void isJwtValidHappyCase(){
        String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJsaXN0LWJhY2tlbmQiLCJpZCI6MiwidXNlciI6InVzZXIifQ.CDMkMXAZIltDlYT-s7nU2reMXW6ytIwfBz4R8bGsQAk";

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

        String username = sut.decode(jwt).getUser();

        assertThat(username).isEqualTo("user");
    }
}
