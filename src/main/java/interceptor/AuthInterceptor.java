
package interceptor;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@Protected
@Priority(Interceptor.Priority.APPLICATION)

public class AuthInterceptor{

    @AroundInvoke
    public Object aroundInvoke(final InvocationContext invocationContext) throws Exception{
        System.out.println("Hello interceptor");
        return true;
    }

}

