import dto.UserDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserResourceTest{

    @Mock
    private final UserRepository userRepositoryMock = mock(UserRepository.class);

    // Mock not working yet

    //@Test
    void loginTest(){
        UserDto userDto = new UserDto(0, "c3d4856944538698d6cf217896afb58df4478e0f902a1c84bf897779c644deec", "username", null);
        // TODO This mock is nice and well, but never injected into the sut instance, so the test throws a NullPointer
        when(userRepositoryMock.get(userDto)).thenReturn(userDto);
        UserResource sut = new UserResource();

        String jwt = (String) sut.login(userDto).getEntity();

        assertThat(jwt.length()).isEqualTo(64);

    }

}
