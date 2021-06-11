import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpStatus;
import dto.UserDto;
import dto.a;
import entity.UserEntity;
import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.UserRepository;
import service.DatabaseService;
import service.JWTService;

import javax.persistence.Persistence;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@EnableWeld
class UserComponentTest{
    final static String H2_PERSISTENCE_UNIT = "list-db-test-h2";

    @WeldSetup
    private final WeldInitiator weld =
            WeldInitiator.from(
                    UserResource.class,
                    JWTService.class,
                    UserRepository.class,
                    DatabaseService.class
            )
                    .setPersistenceContextFactory(i -> Persistence.createEntityManagerFactory(H2_PERSISTENCE_UNIT).createEntityManager())
                    .build();

    @AfterEach
    void afterEach(){
        DatabaseService databaseService = weld.select(DatabaseService.class).get();
        databaseService.getEM().getTransaction().begin();
        databaseService.getEM().createQuery("DELETE FROM UserEntity u");
        databaseService.getEM().getTransaction().commit();
    }

    @Test
    @DisplayName("GIVEN valid user WHEN user is logging in THEN receive a jwt with the username claim set")
    public void loginComponentTest(){
        UserResource sut = weld.select(UserResource.class).get();
        JWTService jwtService = weld.select(JWTService.class).get();
        DatabaseService databaseService = weld.select(DatabaseService.class).get();
        UserDto expected = a.UserDtoBuilder()
                .withId(1)
                .withUsername("cave_johnson")
                .withPasswordSHA256("EA52E694C48FC192C291E1EE5D4C879B2CCB622B77F25FC23E0E3CC586940669")
                .withLists(null)
                .build();
        databaseService.getEM().getTransaction().begin();
        databaseService.getEM().persist(expected.toUserEntity());
        databaseService.getEM().getTransaction().commit();

        String jwt = (String) sut.login(expected).getEntity();

        assertThat(jwtService.isJwtValid(jwt)).isTrue();
        assertThat(jwtService.getUsername(jwt)).isEqualTo("cave_johnson");
    }

    @Test
    @DisplayName("GIVEN invalid user WHEN user is logging in THEN receive a bad request answer")
    public void invalidLoginComponentTest(){
        UserResource sut = weld.select(UserResource.class).get();
        DatabaseService databaseService = weld.select(DatabaseService.class).get();
        UserDto expected = a.UserDtoBuilder()
                .withUsername("ca")
                .withPasswordSHA256("root")
                .build();
        databaseService.getEM().getTransaction().begin();
        databaseService.getEM().persist(expected.toUserEntity());
        databaseService.getEM().getTransaction().commit();

        Response response = sut.login(expected);

        assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    @DisplayName(
            "GIVEN new, valid and unregistered user " +
            "WHEN user is registering " +
            "THEN receive a jwt with the username claim set and have the database updated with the new user"
    )
    void registerComponentTest(){
        UserResource sut = weld.select(UserResource.class).get();
        JWTService jwtService = weld.select(JWTService.class).get();
        DatabaseService databaseService = weld.select(DatabaseService.class).get();
        UserDto expected = a.UserDtoBuilder()
                .withId(1)
                .withUsername("cave_johnson")
                .withPasswordSHA256("EA52E694C48FC192C291E1EE5D4C879B2CCB622B77F25FC23E0E3CC586940669")
                .withLists(null)
                .build();

        databaseService.getEM().getTransaction().begin();
        String jwt = (String) sut.register(expected).getEntity();
        databaseService.getEM().getTransaction().commit();


        assertThat(jwtService.isJwtValid(jwt)).isTrue();
        assertThat(jwtService.getUsername(jwt)).isEqualTo("cave_johnson");
        List<UserEntity> userEntitiesInDb = databaseService.getEM().createQuery("SELECT u FROM UserEntity u").getResultList();
        assertThat(userEntitiesInDb.size()).isOne();
        assertThat(userEntitiesInDb.get(0).toUserDto())
                .usingRecursiveComparison()
                .ignoringFields("lists")
                .isEqualTo(expected);
        assertThat(userEntitiesInDb.get(0).toUserDto().getLists()).isEmpty();

    }

    @Test
    @DisplayName(
            "GIVEN valid and registered user " +
                    "WHEN user is registering " +
                    "THEN receive the answer unauthorized"
    )
    void duplicateRegisterComponentTest(){
        UserResource sut = weld.select(UserResource.class).get();
        JWTService jwtService = weld.select(JWTService.class).get();
        DatabaseService databaseService = weld.select(DatabaseService.class).get();
        UserDto expected = a.UserDtoBuilder()
                .withId(1)
                .withUsername("cave_johnson")
                .withPasswordSHA256("EA52E694C48FC192C291E1EE5D4C879B2CCB622B77F25FC23E0E3CC586940669")
                .withLists(null)
                .build();
        databaseService.getEM().getTransaction().begin();
        databaseService.getEM().persist(expected.toUserEntity());
        databaseService.getEM().getTransaction().commit();

        databaseService.getEM().getTransaction().begin();
        String jwt = (String) sut.register(expected).getEntity();
        databaseService.getEM().getTransaction().commit();

        assertThat(jwtService.isJwtValid(jwt)).isTrue();
        assertThat(jwtService.getUsername(jwt)).isEqualTo("cave_johnson");
        List<UserEntity> userEntitiesInDb = databaseService.getEM().createQuery("SELECT u FROM UserEntity u").getResultList();
        assertThat(userEntitiesInDb.size()).isOne();
        assertThat(userEntitiesInDb.get(0).toUserDto())
                .usingRecursiveComparison()
                .ignoringFields("lists")
                .isEqualTo(expected);
        assertThat(userEntitiesInDb.get(0).toUserDto().getLists()).isEmpty();

    }


}
