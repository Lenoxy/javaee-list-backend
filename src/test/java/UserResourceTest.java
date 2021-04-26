import dto.UserDto;
import dto.a;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.UserRepository;
import service.JWTService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserResourceTest{

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private JWTService jwtServiceMock;

    @InjectMocks
    private UserResource sut;


    @Test
    void loginTest(){
        UserDto userDto = new UserDto(0, "c3d4856944538698d6cf217896afb58df4478e0f902a1c84bf897779c644deec", "username", null);
        when(userRepositoryMock.get(userDto)).thenReturn(userDto);
        when(jwtServiceMock.createJwt(any())).thenReturn("testJWT");

        String jwt = (String) sut.login(userDto).getEntity();

        assertThat(jwt).isEqualTo("testJWT");
    }

    @Test
    void registerTest(){
        UserDto userDto = a.UserDtoWithoutLists();
        when(jwtServiceMock.createJwt(any())).thenReturn("testJWT");

        String jwt = (String) sut.register(userDto).getEntity();

        assertThat(jwt).isEqualTo("testJWT");
    }


}
