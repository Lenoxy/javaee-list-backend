package entity;

import javax.persistence.*;

@Entity
@Table(name = "list_list")
public class ListEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity owner;

    public ListEntity(){
    }

    public ListEntity(UserEntity owner){
        this.owner = owner;
    }

    public int getId(){
        return id;
    }

    public UserEntity getOwner(){
        return owner;
    }

    public void setOwner(UserEntity owner){
        this.owner = owner;
    }
}
