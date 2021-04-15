package entity;

import dto.UserDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "list_user")
public class UserEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String passwordSHA256;

    @OneToMany(
            mappedBy = "owner",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ListEntity> lists = new ArrayList<>();

    public UserEntity(){
    }

    public UserEntity(int id, String username, String passwordSHA256, List<ListEntity> listEntities){
        this.id = id;
        this.username = username;
        this.passwordSHA256 = passwordSHA256;
        this.lists = listEntities;
    }

    public UserDto toUserDto(){
        return new UserDto(
                id,
                passwordSHA256,
                username,
                lists.stream()
                        .map(ListEntity::toListDto)
                        .collect(Collectors.toList())
        );
    }

    public List<ListEntity> getLists(){
        return lists;
    }

    public void setLists(List<ListEntity> lists){
        this.lists = lists;
    }

    public void addListEntity(ListEntity listEntity){
        this.lists.add(listEntity);
        listEntity.setOwner(this);
    }

    public int getId(){
        return id;
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
}
