package entity;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "list_list")
public class ListEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonbTransient
    private UserEntity owner;
    @OneToMany(
            mappedBy = "list",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ItemEntity> items = new ArrayList<>();

    public ListEntity(){
    }

    public ListEntity(String title){
        this.title = title;
    }

    public int getId(){
        return id;
    }

    @JsonbTransient
    public UserEntity getOwner(){
        return owner;
    }

    @JsonbTransient
    public void setOwner(UserEntity owner){
        this.owner = owner;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public List<ItemEntity> getItems(){
        return items;
    }

    public void setItems(List<ItemEntity> items){
        this.items = items;
    }

    public void addItem(ItemEntity item){
        item.setList(this);
        this.items.add(item);
    }
}
