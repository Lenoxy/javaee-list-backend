package dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ListDtoTest{
    @Test
    void toListEntityAndBack(){
        ListDto sut = a.ListDto();
        assertThat(sut.toListEntity().toListDto()).usingRecursiveComparison().isEqualTo(sut);
    }
}
