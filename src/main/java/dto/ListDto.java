package dto;

import java.util.List;

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
