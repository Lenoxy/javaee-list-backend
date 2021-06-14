package entity;

import dto.ItemDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "list_item")
public class ItemEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    private ListEntity list;

    public ItemEntity(){
    }

    public ItemEntity(int id, String content){
        this.id = id;
        this.content = content;
    }

    public ItemDto toItemDto(){
        return new ItemDto(id, content);
    }
}
