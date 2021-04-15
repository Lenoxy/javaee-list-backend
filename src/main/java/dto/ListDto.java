package dto;

import entity.ListEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ListDto{
    private int id;
    private String title;
    private List<ItemDto> items;

    public ListDto(){
    }

    public ListDto(int id, String title, List<ItemDto> items){
        this.id = id;
        this.title = title;
        this.items = items;
    }

    public ListEntity toListEntity(){
        return new ListEntity(
                title,
                items.stream().map(ItemDto::toItemEntity).collect(Collectors.toList())
        );
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public List<ItemDto> getItems(){
        return items;
    }

    public void setItems(List<ItemDto> items){
        this.items = items;
    }
}
