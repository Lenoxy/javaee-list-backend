import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

@Dependent
public class Database{

    private static Database instance;
    @PersistenceContext
    public EntityManager entityManager;

    public Database(){
        System.out.println("init db called");
        entityManager = Persistence.createEntityManagerFactory("list-db").createEntityManager();
    }

    public static Database getInstance(){
        if(instance == null){
            instance = new Database();
        }

        return instance;
    }
}
