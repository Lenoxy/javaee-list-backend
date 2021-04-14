package dto;

import entity.UserEntity;

import java.util.List;

public class UserDto{

    private int id;
    private String username;
    private String passwordSHA256;
    private List<ListDto> lists;


    public UserDto(int id, String passwordSHA256, String username, List<ListDto> lists){
        this.id = id;
        this.passwordSHA256 = passwordSHA256;
        this.username = username;
        this.lists = lists;
    }

    public boolean isInvalid(){

        if(username == null || username.length() < 3){
            return true;
        }
        if(passwordSHA256 == null /*|| passwordSHA256.length()  != 64*/){
            return true;
        }
//        if(email == null || ! email.matches("[^@]+@[^\\.]+\\..+")){
//            return false;
//        }

        return false;
    }

    public UserEntity toUserEntity(){
        return new UserEntity(
                username,
                passwordSHA256,
                null
        );
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPasswordSHA256(){
        return passwordSHA256;
    }

    public void setPasswordSHA256(String passwordSHA256){
        this.passwordSHA256 = passwordSHA256;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public List<ListDto> getLists(){
        return lists;
    }

    public void setLists(List<ListDto> lists){
        this.lists = lists;
    }
}
