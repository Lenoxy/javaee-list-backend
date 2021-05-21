package weldi;

import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.Test;
import service.JWTService;

import javax.inject.Inject;

@EnableWeld
class SomeCDIBeanTest {


    @WeldSetup
    private final WeldInitiator weld =
            WeldInitiator.from(
                    SomeCDIBean.class, SomeOtherCDIBean.class
            )
                    //.setPersistenceContextFactory(i -> Persistence.createEntityManagerFactory(H2_PERSISTENCE_UNIT).createEntityManager())
                    .build();

    @Test
    public void testSth() {
        SomeOtherCDIBean someOtherCDIBean = weld.select(SomeOtherCDIBean.class).get();

        System.out.println("select.toString() = " + someOtherCDIBean.doSth());

    }

    @Test
    void registerTest() {

    }


}
