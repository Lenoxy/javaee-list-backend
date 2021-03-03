package entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Benutzer", schema="public")
public class User {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    // Todo HASH
    @Column(name = "plainPassword")
    private String plainPassword;


    public User(){
    }

    public User(int id, String username, String plainPassword){
        this.id = id;
        this.username = username;
        this.plainPassword = plainPassword;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
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
