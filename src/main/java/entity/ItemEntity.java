package entity;

import javax.persistence.*;

@Entity
@Table(name = "list_item")
public class ItemEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private ListEntity list;

    public ItemEntity(){
    }

    public ItemEntity(String content){
        this.content = content;
    }

    public int getId(){
        return id;
    }

    public ListEntity getList(){
        return list;
    }

    public void setList(ListEntity list){
        this.list = list;
    }

    @Override
    public String toString(){
        return "ItemEntity{" +
                "id=" + id +
                ", list=" + list +
                '}';
    }
}
