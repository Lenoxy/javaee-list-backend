import entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.List;

@ApplicationPath("")
public class RestApplication extends Application{
    public static void main(String[] args) {
        System.out.println("main calledbui");

        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-unit");
            EntityManager em = emf.createEntityManager();

            List<User> users = em.createQuery("SELECT * FROM User").getResultList();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
