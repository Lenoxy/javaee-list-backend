import dto.a;
import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.Test;
import repository.UserRepository;
import service.DatabaseService;
import service.JWTService;

import javax.persistence.Persistence;

@EnableWeld
class UserComponentTest{

    final static String H2_PERSISTENCE_UNIT = "list-db-test-h2";

    @WeldSetup // This tells weld to consider only Bar, nothing else
    public WeldInitiator weld =
            WeldInitiator.from(
                    UserRepository.class,
                    JWTService.class,
                    DatabaseService.class,
                    UserResource.class
            )
            .setPersistenceContextFactory(i -> Persistence.createEntityManagerFactory(H2_PERSISTENCE_UNIT).createEntityManager())
            .build();


    @Test
    void loginTest(){
        final UserResource sut = weld.select(UserResource.class).get();
        sut.login(a.UserDtoBuilder().build());
    }

    @Test
    void registerTest(){

    }


}
