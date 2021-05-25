import dto.UserDto;
import dto.a;
import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.Test;
import repository.UserRepository;
import service.DatabaseService;
import service.JWTService;

import javax.persistence.Persistence;
import javax.ws.rs.core.Response;

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

    @Test
    public void loginComponentTest(){
        UserDto expected = a.UserDtoBuilder()
                .withId(1)
                .withUsername("cave_johnson")
                .withPasswordSHA256("EA52E694C48FC192C291E1EE5D4C879B2CCB622B77F25FC23E0E3CC586940669")
                .withLists(null)
                .build();

        DatabaseService databaseService = weld.select(DatabaseService.class).get();
        databaseService.getEntityManager().persist(expected.toUserEntity());


        UserResource sut = weld.select(UserResource.class).get();


        Response actual = sut.login(expected);

        assertThat((String) actual.getEntity()).hasSize(64);
    }

    @Test
    void registerTest(){

    }


}
