package entity;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "list_user")
public class UserEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    // Todo HASH
    @Column(name = "plainPassword")
    private String plainPassword;
    @OneToMany(
            mappedBy = "owner",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )

    private List<ListEntity> lists = new ArrayList<>();

    public UserEntity(){
    }

    public UserEntity(String username, String plainPassword, List<ListEntity> listEntities){
        this.username = username;
        this.plainPassword = plainPassword;
        this.lists = listEntities;
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

    public String getPlainPassword(){
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword){
        this.plainPassword = plainPassword;
    }
}
