package dto;

import entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserDto{

    private Integer id;
    private String username;
    private String passwordSHA256;
    private List<ListDto> lists;


    public UserDto(Integer id, String passwordSHA256, String username, List<ListDto> lists){
        this.id = id;
        this.passwordSHA256 = passwordSHA256;
        this.username = username;
        this.lists = lists;
    }

    public UserDto(){
    }

    public boolean isValid(){
        if(username == null || username.length() < 3){
            return false;
        }
        if(passwordSHA256 == null || passwordSHA256.length() != 64){
            return false;
        }

        return true;
    }

    public UserEntity toUserEntity(){
        return new UserEntity(
                id,
                username,
                passwordSHA256,
                lists != null ? lists.stream().map(ListDto::toListEntity).collect(Collectors.toList()) : null
        );
    }
}
