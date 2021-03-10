package entity;

import javax.persistence.*;

@Entity
@Table(name = "listUser")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    // Todo HASH
    @Column(name = "plainPassword")
    private String plainPassword;

    public User(){
    }

    public User(String username, String plainPassword){
        this.username = username;
        this.plainPassword = plainPassword;
    }

    @Override
    public String toString(){
        return "{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", plainPassword='" + plainPassword + '\'' +
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
