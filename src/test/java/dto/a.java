package dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class a{
    public static UserDto ValidUserDto(){
        return userDto(new Random().nextInt());
    }

    public static UserDto InvalidUserDto(){
        return new UserDto(
                -1,
                "not sha256 encrypted",
                "us",
                null
        );
    }

    private static UserDto userDto(int random){
        return new UserDto(
                random,
                "c3d4856944538698d6cf217896afb58df4478e0f902a1c84bf897779c644deec",
                String.valueOf(random),
                listDtoList()
        );
    }

    private static List<ListDto> listDtoList(){
        List<ListDto> listDtoList = new ArrayList<>();
        listDtoList.add(ListDto());
        listDtoList.add(ListDto());
        listDtoList.add(ListDto());
        return listDtoList;
    }

    public static ListDto ListDto(){
        return listDto(new Random().nextInt());
    }

    private static ListDto listDto(int random){
        return new ListDto(
                random,
                String.valueOf(random),
                itemDtoList()
        );
    }

    private static List<ItemDto> itemDtoList(){
        List<ItemDto> itemDtoList = new ArrayList<>();
        itemDtoList.add(ItemDto());
        itemDtoList.add(ItemDto());
        itemDtoList.add(ItemDto());
        return itemDtoList;
    }

    public static ItemDto ItemDto(){
        return itemDto(new Random().nextInt());
    }

    private static ItemDto itemDto(int random){
        return new ItemDto(
                random,
                String.valueOf(random)
        );
    }
}
