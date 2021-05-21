import org.jboss.weld.junit5.WeldJunit5Extension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import service.SomeCDIBean;

import javax.inject.Inject;

//@EnableWeld
@ExtendWith(WeldJunit5Extension.class)
class UserComponentTest{

  //  final static String H2_PERSISTENCE_UNIT = "list-db-test-h2";

//    @WeldSetup
//    public WeldInitiator weld =
//            WeldInitiator.from(
//                    //UserRepository.class,
//                    JWTService.class
//                    //DatabaseService.class
//            )
//                    //.setPersistenceContextFactory(i -> Persistence.createEntityManagerFactory(H2_PERSISTENCE_UNIT).createEntityManager())
//                    .build();
@Inject
SomeCDIBean jwtService;


    @Test
    public void loginTest(){
        //JWTService select = weld.select(JWTService.class).get();

        System.out.println("select.toString() = " + jwtService.toString());

//        sut.login(a.UserDtoBuilder().build());
    }

    @Test
    void registerTest(){

    }


}
