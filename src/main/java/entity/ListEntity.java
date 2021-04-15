package entity;

import dto.ListDto;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private UserEntity owner;

    @OneToMany(
            mappedBy = "list",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ItemEntity> items = new ArrayList<>();

    public ListEntity(){
    }

    public ListEntity(int id, String title, List<ItemEntity> items){
        this.id = id;
        this.title = title;
        this.items = items;
    }

    public ListDto toListDto(){
        return new ListDto(
                id,
                title,
                items.stream().map(ItemEntity::toItemDto).collect(Collectors.toList())
        );
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
