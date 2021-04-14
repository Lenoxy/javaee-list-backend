import entity.ItemEntity;
import entity.ListEntity;
import entity.UserEntity;
import org.junit.jupiter.api.Test;

import javax.json.bind.JsonbBuilder;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AuthResourceTest{
    private final ItemEntity itemEntity1 = new ItemEntity("content1");
    private final ItemEntity itemEntity2 = new ItemEntity("content2");
    private final ItemEntity itemEntity3 = new ItemEntity("content3");
    private final ItemEntity itemEntity4 = new ItemEntity("content4");

    private final ListEntity listEntity1 = new ListEntity("list1");
    private final ListEntity listEntity2 = new ListEntity("list2");

    private final UserEntity userEntity = new UserEntity("username123", "plainSecurePassword", new ArrayList<>());

    //@Test
    public void jsonBRecursiveTest(){
        listEntity1.addItem(itemEntity1);
        listEntity1.addItem(itemEntity2);
        listEntity2.addItem(itemEntity3);
        listEntity2.addItem(itemEntity4);

        userEntity.addListEntity(listEntity1);
        userEntity.addListEntity(listEntity2);

        String json = JsonbBuilder.create().toJson(userEntity);

        UserEntity userEntityFromJson = JsonbBuilder.create().fromJson(json, UserEntity.class);

        // These fields must be ignored during serialization in order to break recursion.
        // In deserialization, these fields are missing for obvious reasons.
        assertThat(userEntityFromJson).usingRecursiveComparison().ignoringFields("lists.owner", "lists.items.list").isEqualTo(userEntity);
    }
}
