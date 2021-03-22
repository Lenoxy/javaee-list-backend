package entity;

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

    @ManyToOne(fetch=FetchType.LAZY)
    private UserEntity owner;

    @OneToMany(
            mappedBy = "list",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ItemEntity> items = new ArrayList<>();

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

    @Override
    public String toString(){
        return "ListEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", owner=" + owner +
                ", items=" + items +
                '}';
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

    public void addItem(ItemEntity item){
        item.setList(this);
        this.items.add(item);
    }
}
