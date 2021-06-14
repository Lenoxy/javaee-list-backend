package entity;

import dto.UserDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "list_user")
public class UserEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public void addListEntity(ListEntity listEntity){
        this.lists.add(listEntity);
        listEntity.setOwner(this);
    }
}
