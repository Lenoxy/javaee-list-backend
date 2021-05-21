package weldi;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class SomeOtherCDIBean {

    @Inject
    private SomeCDIBean someCDIBean;

    public String doSth(){
        return someCDIBean.doSth();
    }

}
