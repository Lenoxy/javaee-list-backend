package entity;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class a{
    public static UserEntityBuilder UserEntityBuilder(){
        return new UserEntityBuilder();
    }

    public static ListEntityBuilder ListEntityBuilder(){
        return new ListEntityBuilder();
    }

    public static ItemEntityBuilder ItemEntityBuilder(){
        return new ItemEntityBuilder();
    }

    public static class UserEntityBuilder{
        private int id;
        private String passwordSHA256;
        private String username;
        private List<ListEntity> lists;

        private UserEntityBuilder(){
            this.id = new Random().nextInt();
            this.passwordSHA256 = "c3d4856944538698d6cf217896afb58df4478e0f902a1c84bf897779c644deec";
            this.username = String.valueOf(new Random().nextInt());
            this.lists = Collections.singletonList(a.ListEntityBuilder().build()
            );
        }

        public UserEntityBuilder withId(int id){
            this.id = id;
            return this;
        }

        public UserEntityBuilder withPasswordSHA256(String passwordSHA256){
            this.passwordSHA256 = passwordSHA256;
            return this;
        }

        public UserEntityBuilder withUsername(String username){
            this.username = username;
            return this;
        }

        public UserEntityBuilder withLists(List<ListEntity> lists){
            this.lists = lists;
            return this;
        }

        public UserEntity build(){
            return new UserEntity(
                    id,
                    passwordSHA256,
                    username,
                    lists
            );
        }
    }


    public static class ListEntityBuilder{
        private int id;
        private String title;
        private List<ItemEntity> items;

        private ListEntityBuilder(){
            this.id = new Random().nextInt();
            this.title = String.valueOf(new Random().nextInt());
            this.items = Collections.singletonList(a.ItemEntityBuilder().build());
        }

        public ListEntityBuilder withId(int id){
            this.id = id;
            return this;
        }

        public ListEntityBuilder withTitle(String username){
            this.title = username;
            return this;
        }

        public ListEntityBuilder withItems(List<ItemEntity> items){
            this.items = items;
            return this;
        }

        public ListEntity build(){
            return new ListEntity(
                    id,
                    title,
                    items
            );
        }
    }

    public static class ItemEntityBuilder{
        private int id;
        private String content;

        private ItemEntityBuilder(){
            this.id = new Random().nextInt();
            this.content = String.valueOf(new Random().nextInt());
        }

        public ItemEntityBuilder withId(int id){
            this.id = id;
            return this;
        }

        public ItemEntityBuilder withContent(String content){
            this.content = content;
            return this;
        }

        public ItemEntity build(){
            return new ItemEntity(
                    id,
                    content
            );
        }
    }
}
