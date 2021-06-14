package dto;

import entity.ItemEntity;
import lombok.Getter;

@Getter
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
}
