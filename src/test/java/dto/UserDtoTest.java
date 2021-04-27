package dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserDtoTest{

    @Test
    void toUserEntityAndBack(){
        UserDto sut = a.UserDtoBuilder().build();

        UserDto actual = sut.toUserEntity().toUserDto();

        assertThat(sut).usingRecursiveComparison().isEqualTo(actual);


    }

    @Test
    void isValidHappyCaseTest(){
        boolean valid = a.UserDtoBuilder().build().isValid();
        assertThat(valid).isTrue();
    }

    @Test
    void isValidBadCaseTest(){
        boolean valid = a.UserDtoBuilder().withUsername("ab").withPasswordSHA256("notsha256").build().isValid();
        assertThat(valid).isFalse();
    }

}
