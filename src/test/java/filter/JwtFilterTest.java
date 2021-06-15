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
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.HttpHeaders;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtFilterTest{

    @Mock
    private ContainerRequestContext ctxMock;

    @Mock
    private ResourceInfo resourceInfoMock;

    @Mock
    private JwtService jwtServiceMock;

    @InjectMocks
    private JwtFilter sut;

    @Test
    public void filterLoginToAuthEndpoints(){
        mockMethodAnnotatedWithLoginRequired(false);
        sut.filter(ctxMock);
    }

    @Test
    void filterLoginWithoutBearer(){
        mockMethodAnnotatedWithLoginRequired(true);
        when(ctxMock.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn(null);

        assertThatThrownBy(() ->
                sut.filter(ctxMock)
        ).isInstanceOf(BearerMissingException.class);
    }

    @Test
    void filterLoginWithInvalidBearer(){
        mockMethodAnnotatedWithLoginRequired(true);
        when(ctxMock.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn("any-invalid-bearer");
        when(jwtServiceMock.isJwtValid("any-invalid-bearer")).thenReturn(false);

        assertThatThrownBy(
                () -> sut.filter(ctxMock)
        ).isInstanceOf(BearerInvalidException.class);
    }

    @Test
    void filterLoginWithUserClaimMissing(){
        mockMethodAnnotatedWithLoginRequired(true);
        when(ctxMock.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn("valid-bearer");
        when(jwtServiceMock.getUsername("valid-bearer")).thenReturn(null);
        when(jwtServiceMock.isJwtValid("valid-bearer")).thenReturn(true);

        assertThatThrownBy(
                () -> sut.filter(ctxMock)
        ).isInstanceOf(UserClaimMissingException.class);
    }

    private void mockMethodAnnotatedWithLoginRequired(boolean loginRequired){
        try{
            if(loginRequired){
                when(resourceInfoMock.getResourceMethod()).thenReturn(getClass().getMethod("methodWithAnnotation"));
            }else{
                when(resourceInfoMock.getResourceMethod()).thenReturn(getClass().getMethod("methodWithoutAnnotation"));
            }
        }catch(NoSuchMethodException e){
            e.printStackTrace();
        }
    }

    @RequiresLogin
    public void methodWithAnnotation(){
    }

    public void methodWithoutAnnotation(){
    }
}

