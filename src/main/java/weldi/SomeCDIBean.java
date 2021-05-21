package weldi;

import javax.enterprise.context.Dependent;

@Dependent
public class SomeCDIBean{

    public String doSth(){
        return "Hallo Welt";
    }

}
