package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.json.bind.annotation.JsonbTransient;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonbTransient
    private ListEntity list;

    public ItemEntity(){
    }

    public ItemEntity(String content){
        this.content = content;
    }

    public int getId(){
        return id;
    }

    @JsonbTransient
    public ListEntity getList(){
        return list;
    }

    @JsonbTransient
    public void setList(ListEntity list){
        this.list = list;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }
}
