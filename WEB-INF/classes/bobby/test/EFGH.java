package bobby.test;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;
@Path("/efgh")
public class EFGH
{
@Forward("/efgh/tom")
@Path("/sam")
public void sam()
{
System.out.println("Sam got invoked");
}

@Path("/tom")
@Forward("/student/updateStudent")
public int tom()
{
System.out.println("Tom got invoked");
return 99;
}
@Path("/justDoIt")
public void justDoIt()
{
int a=5/0;
}
}
