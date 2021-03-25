package bobby.test.firstApp;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;

public class Student
{
private int rollNumber;
private String name;
private char gender;
public Student()
{
this.rollNumber=0;
this.name="";
this.gender=0;
}
public void setRollNumber(int rollNumber)
{
this.rollNumber=rollNumber;
}
public int getRollNumber()
{
return this.rollNumber;
}
public void setName(String name)
{
this.name=name;
}
public String getName()
{
return this.name;
}
public void setGender(char gender)
{
this.gender=gender;
}
public char getGender()
{
return this.gender;
}
}
