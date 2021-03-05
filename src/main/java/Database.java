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
        try{
            entityManager = Persistence.createEntityManagerFactory("list-db").createEntityManager();

            entityManager.createNativeQuery("INSERT INTO Benutzer (username, plainPassword) VALUES ('lenoxy','yyyyd')");

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public String getName(){
        return "Hi from the db";
    }
}
