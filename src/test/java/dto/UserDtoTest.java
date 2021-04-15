package dto;

import entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

class UserDtoTest{
    final static UserDto validUserDto1 = new UserDto(
            1,
            "212a441762ad8f8bae92e6b667813f4539df7cdd7a9d210e5400da636fba1949",
            "Fritz",
            new ArrayList<>()
    );
    final static UserDto validUserDto2 = new UserDto(
            2,
            "155a7686895f1c18de7c756cf36099418f7bae2b1ab378ea188153d43d88e688",
            "Hans",
            new ArrayList<>()
    );
    final static UserDto invalidUserDto = new UserDto(
            3,
            "hunter2",
            "Peter",
            new ArrayList<>()
    );

    private static Stream<UserDto> userDtoStream(){
        return Stream.of(validUserDto1, validUserDto2, invalidUserDto);
    }
    // That's java for you


    @ParameterizedTest
    @MethodSource("userDtoStream")
    void toUserEntityTest(UserDto userDto){
        UserEntity userEntity = userDto.toUserEntity();

        assertThat(userEntity.getUsername()).isEqualTo(userDto.getUsername());
        assertThat(userEntity.getPasswordSHA256()).isEqualTo(userDto.getPasswordSHA256());
    }

    @Test
    void isValidHappyCaseTest(){
        boolean valid = validUserDto1.isValid();
        assertThat(valid).isTrue();
    }

    @Test
    void isValidBadCaseTest(){
        boolean valid = invalidUserDto.isValid();
        assertThat(valid).isFalse();
    }

}
