package dto;

import entity.ListEntity;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
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
                id,
                title,
                items.stream().map(ItemDto::toItemEntity).collect(Collectors.toList())
        );
    }
}
