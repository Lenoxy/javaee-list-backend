import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("")
public class RestApplication extends Application{
    /*
        System.out.println("main called");

        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-unit");
            EntityManager em = emf.createEntityManager();

            List<User> users = em.createQuery("SELECT * FROM User").getResultList();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    */
}
