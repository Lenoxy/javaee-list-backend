package dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class a{
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

    public static UserDtoBuilder UserDtoBuilder(){
        return new UserDtoBuilder();
    }

    public static class UserDtoBuilder{
        private int id;
        private String passwordSHA256;
        private String username;
        private List<ListDto> lists;

        private UserDtoBuilder(){
            this.id = new Random().nextInt();
            this.passwordSHA256 = "c3d4856944538698d6cf217896afb58df4478e0f902a1c84bf897779c644deec";
            this.username = String.valueOf(new Random().nextInt());
            this.lists = listDtoList();
        }

        public UserDtoBuilder withId(int id){
            this.id = id;
            return this;
        }

        public UserDtoBuilder withPasswordSHA256(String passwordSHA256){
            this.passwordSHA256 = passwordSHA256;
            return this;
        }

        public UserDtoBuilder withUsername(String username){
            this.username = username;
            return this;
        }

        public UserDtoBuilder withLists(List<ListDto> lists){
            this.lists = lists;
            return this;
        }

        public UserDto build(){
            return new UserDto(
                    id,
                    passwordSHA256,
                    username,
                    lists
            );
        }
    }
}
