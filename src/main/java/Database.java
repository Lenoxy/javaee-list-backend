import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;

@Dependent
public class Database{
    public Database(){
        //System.err.println("Hi from init in class: " + this.getClass().getName() + ".construct()");
    }

    @PostConstruct
    public void init(){
        //System.err.println("Hi from init in class: " + this.getClass().getName() + ".init()");
    }

    public String getName(){
        return "Hi from the db service";
    }
}
