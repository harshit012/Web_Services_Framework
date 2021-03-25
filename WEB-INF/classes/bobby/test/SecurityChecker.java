package bobby.test;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;

@InjectApplicationScope
public class SecurityChecker
{
private ApplicationScope applicationScope;
public void validateUser() throws Exception
{
System.out.println("validate user method of SecurityChecker got invoked");
System.out.println("Data in applicationScope:"+this.applicationScope.getAttribute("123"));
throw new Exception("SecurityException in validateUser");
}
public void setApplicationScope(ApplicationScope applicationScope)
{
System.out.println("setApplicationScope of SecurityChecker got invoked");
this.applicationScope=applicationScope;
}
}