import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class DatabaseInitializer{
    @PostConstruct
    public void init() {
        System.out.println("Hi from init in class: " + this.getClass().getName());
    }
}
