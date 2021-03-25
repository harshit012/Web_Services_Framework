package bobby.test;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;

@Path("/login")
public class login
{
@Path("/validate")
public void validate(ApplicationScope applicationScope)
{
System.out.println("validate method got invoked");
applicationScope.setAttribute("123","456");
}
}