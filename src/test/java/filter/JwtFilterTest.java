package filter;

import exception.BearerInvalidException;
import exception.BearerMissingException;
import exception.UserClaimMissingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.JwtService;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtFilterTest{

    @Mock
    private ContainerRequestContext ctxMock;

    @Mock
    private UriInfo uriInfoMock;

    @Mock
    private JwtService jwtServiceMock;

    @InjectMocks
    private JwtFilter sut;

    @BeforeEach
    void setUp(){
        when(ctxMock.getUriInfo()).thenReturn(uriInfoMock);
    }

    @Test
    void filterLoginToAuthEndpoints(){
        when(uriInfoMock.getPath()).thenReturn("auth");

        sut.filter(ctxMock);
    }

    @Test
    void filterLoginWithoutBearer(){
        when(uriInfoMock.getPath()).thenReturn("list");
        when(ctxMock.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn(null);
        assertThatThrownBy(
                () -> sut.filter(ctxMock)
        ).isInstanceOf(BearerMissingException.class);
    }

    @Test
    void filterLoginWithInvalidBearer(){
        when(uriInfoMock.getPath()).thenReturn("list");
        when(ctxMock.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn("any-invalid-bearer");
        when(jwtServiceMock.isJwtValid("any-invalid-bearer")).thenReturn(false);

        assertThatThrownBy(
                () -> sut.filter(ctxMock)
        ).isInstanceOf(BearerInvalidException.class);
    }

    @Test
    void filterLoginWithUserClaimMissing(){
        when(uriInfoMock.getPath()).thenReturn("list");
        when(ctxMock.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn("valid-bearer");
        when(jwtServiceMock.getUsername("valid-bearer")).thenReturn(null);
        when(jwtServiceMock.isJwtValid("valid-bearer")).thenReturn(true);

        assertThatThrownBy(
                () -> sut.filter(ctxMock)
        ).isInstanceOf(UserClaimMissingException.class);
    }
}
