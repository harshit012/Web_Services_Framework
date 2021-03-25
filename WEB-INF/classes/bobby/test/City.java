package bobby.test;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;
import java.io.*;
public class City implements Serializable
{
private String name;
private int code;
public City()
{
this.name="";
this.code=0;
}
public void setName(String name)
{
this.name=name;
}
public String getName()
{
return this.name;
}
public void setCode(int code)
{
this.code=code;
}
public int getCode()
{
return this.code;
}
}
