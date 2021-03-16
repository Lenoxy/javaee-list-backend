package entity;

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
    private List<ListEntity> listEntities = new ArrayList<>();

    public UserEntity(){
    }

    public UserEntity(String username, String plainPassword, List<ListEntity> listEntities){
        this.username = username;
        this.plainPassword = plainPassword;
        this.listEntities = listEntities;
    }

    public List<ListEntity> getListEntities(){
        return listEntities;
    }

    public void addListEntity(ListEntity listEntity){
        this.listEntities.add(listEntity);
        listEntity.setOwner(this);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", plainPassword='" + plainPassword + '\'' +
                ", listEntities=" + listEntities +
                '}';
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
