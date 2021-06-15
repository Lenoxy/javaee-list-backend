package service;

import lombok.Getter;

@Getter
public class Jwt{
    private String issuer;
    private String user;
    private Integer id;


    public Jwt(String issuer, String user, Integer id){
        this.issuer = issuer;
        this.user = user;
        this.id = id;
    }
}
