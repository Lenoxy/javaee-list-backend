import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@Dependent
public class Database{

    public EntityManager entityManager;

    @PostConstruct
    public void init(){
        System.out.println("init db called");
        entityManager = Persistence.createEntityManagerFactory("list-db").createEntityManager();
    }
}
