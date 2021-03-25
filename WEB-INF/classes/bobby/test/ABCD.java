package bobby.test;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;

@InjectApplicationScope
public class ABCD
{
private Bulb bulb;
private ApplicationScope applicationScope;
@Startup(PRIORITY=1)
public void sam()
{
this.applicationScope.setAttribute("1","Harshit");
System.out.println("&&&&&&&&&&&&&&&&&&&&&Sam got called&&&&&&&&&&&&&&&&&&&&&");
this.bulb=new Bulb();
this.bulb.setWattage(50);
this.applicationScope.setAttribute("xyz",this.bulb);
}
@Startup(PRIORITY=2)
public void tom()
{
this.applicationScope.setAttribute("2","Aman");
System.out.println("&&&&&&&&&&&&&&&&&&&&&tom got called&&&&&&&&&&&&&&&&&&&&&");
}
public void setApplicationScope(ApplicationScope applicationScope)
{
this.applicationScope=applicationScope;
System.out.println("&&&&&&&&&&&&&&&&&&&&&Application scope is set in ABCD.java&&&&&&&&&&&&&&&&&&&&&");
}
}
