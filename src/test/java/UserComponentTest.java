import dto.UserDto;
import dto.a;
import entity.UserEntity;
import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.Test;
import repository.UserRepository;
import service.DatabaseService;
import service.JWTService;

import javax.persistence.Persistence;
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

    @Test
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
    }

    @Test
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

        // TODO: Use transaction here and not in production code
        String jwt = (String) sut.register(expected).getEntity();

        assertThat(jwtService.isJwtValid(jwt)).isTrue();
        List<UserEntity> userEntitiesInDb = databaseService.getEM().createQuery("SELECT u FROM UserEntity u").getResultList();
        assertThat(userEntitiesInDb.size()).isOne();
        assertThat(userEntitiesInDb.get(0).toUserDto())
                .usingRecursiveComparison()
                .ignoringFields("lists")
                .isEqualTo(expected);

    }


}
