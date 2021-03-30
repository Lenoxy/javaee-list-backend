package dto;

import java.io.Serializable;

public class RegisterUserDto implements Serializable{

    public String username;
    public String passwordSHA256;
    public String email;


    public boolean isValid(){
        System.out.println(username);
        System.out.println(passwordSHA256);
        System.out.println(email);


        if(username == null || username.length() < 3){
            return false;
        }
        if(passwordSHA256 == null /*|| passwordSHA256.length()  != 64*/ ){
            return false;
        }
        if(email == null || !email.matches("[^@]+@[^\\.]+\\..+")){
            return false;
        }

        return true;
    }
}
