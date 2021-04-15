package dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserDtoTest{

    @Test
    void toUserEntityAndBack(){
        UserDto sut = a.ValidUserDto();

        UserDto actual = sut.toUserEntity().toUserDto();

        assertThat(sut).usingRecursiveComparison().isEqualTo(actual);


    }

    @Test
    void isValidHappyCaseTest(){
        boolean valid = a.ValidUserDto().isValid();
        assertThat(valid).isTrue();
    }

    @Test
    void isValidBadCaseTest(){
        boolean valid = a.InvalidUserDto().isValid();
        assertThat(valid).isFalse();
    }

}
