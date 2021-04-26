package dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ItemDtoTest{

    @Test
    void toItemEntity(){
        ItemDto sut = a.ItemDto();

        ItemDto actual = sut.toItemEntity().toItemDto();

        assertThat(actual).usingRecursiveComparison().isEqualTo(sut);
    }
}
