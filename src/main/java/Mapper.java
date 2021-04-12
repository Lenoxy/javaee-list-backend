import dto.ItemDto;
import dto.ListDto;
import dto.UserDto;
import entity.ItemEntity;
import entity.ListEntity;
import entity.UserEntity;

import java.util.stream.Collectors;

public class Mapper{

    public static ItemDto toItemDto(ItemEntity itemEntity){
        return new ItemDto(
                itemEntity.getId(),
                itemEntity.getContent()
        );
    }

    public static ListDto toListDto(ListEntity listEntity){
        return new ListDto(
                listEntity.getId(),
                listEntity.getTitle(),
                listEntity.getItems()
                        .stream()
                        .map(Mapper::toItemDto)
                        .collect(Collectors.toList())
        );
    }
    public static UserDto toUserDto(UserEntity userEntity){
        return new UserDto(
                userEntity.getId(),
                userEntity.getPlainPassword(),
                userEntity.getUsername(),
                userEntity.getLists()
                        .stream()
                .map(Mapper::toListDto)
                .collect(Collectors.toList())
        );
    }



}
