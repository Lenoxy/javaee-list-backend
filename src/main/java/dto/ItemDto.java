package dto;

import entity.ItemEntity;

public class ItemDto{
    private int id;
    private String content;

    public ItemDto(){
    }

    public ItemDto(int id, String content){
        this.id = id;
        this.content = content;
    }

    public ItemEntity toItemEntity(){
        return new ItemEntity(id, content);
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }
}
