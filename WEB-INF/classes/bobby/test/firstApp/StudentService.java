package bobby.test.firstApp;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;
import java.util.*;
import java.sql.*;

@Path("/studentService")
public class StudentService
{
private Connection connection;
public StudentService()
{
try
{
Class.forName("com.mysql.jdbc.Driver");
this.connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/school","root","");
}catch(Exception exception)
{
System.out.println(exception.getMessage());
}
}

@Path("/add")
public void add(Student student) throws StudentException
{
System.out.println("add got invoked");
try
{
if(student==null) System.out.println("Student object is null");
int rollNumber=student.getRollNumber();
String name=student.getName();
char gender=student.getGender();
PreparedStatement preparedStatement=null;
if(this.connection==null) System.out.println("Connection is nulll");
preparedStatement=this.connection.prepareStatement("select name from student where rollNumber=?");
preparedStatement.setInt(1,rollNumber);
ResultSet resultSet=null;
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new StudentException("Roll number exists");
}
resultSet.close();
preparedStatement.close();
preparedStatement=this.connection.prepareStatement("insert into student (rollNumber,name,gender) values (?,?,?)");
preparedStatement.setInt(1,rollNumber);
preparedStatement.setString(2,name);
preparedStatement.setString(3,String.valueOf(gender));
preparedStatement.executeUpdate();
preparedStatement.close();
}
catch(SQLException exception)
{
System.out.println(exception.getMessage());
}
System.out.println("returned add got invoked");
}

@Path("/update")
public void update(Student student)  throws StudentException
{
System.out.println("upate got invoked");
try
{
int rollNumber=student.getRollNumber();
String name=student.getName();
char gender=student.getGender();
PreparedStatement preparedStatement=null;
preparedStatement=this.connection.prepareStatement("select name from student where rollNumber=?");
preparedStatement.setInt(1,rollNumber);
ResultSet resultSet=null;
resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new StudentException("Invalid rollNumber");
}
resultSet.close();
preparedStatement.close();
preparedStatement=this.connection.prepareStatement("update student set name=?,gender=? where rollNumber=?");
preparedStatement.setString(1,name);
preparedStatement.setString(2,String.valueOf(gender));
preparedStatement.setInt(3,rollNumber);
preparedStatement.executeUpdate();
preparedStatement.close();
}catch(SQLException exception)
{
System.out.println(exception.getMessage());
}
System.out.println("returned from udpate got invoked");
}

@Path("/delete")
public void delete(@RequestParameter("rollNumber")int rollNumber) throws StudentException
{
System.out.println("delete got invoked");
try
{
PreparedStatement preparedStatement=null;
preparedStatement=this.connection.prepareStatement("select name from student where rollNumber=?");
preparedStatement.setInt(1,rollNumber);
ResultSet resultSet=null;
resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new StudentException("Invalid rollNumber");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from student where rollNumber=?");
preparedStatement.setInt(1,rollNumber);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException exception)
{
System.out.println(exception.getMessage());
}
System.out.println("return delete got invoked");
}

@Path("/getByRollNumber")
public Student getByRollNumber(@RequestParameter("rollNumber")int rollNumber) throws StudentException
{

System.out.println("getByRollNumber got invoked");
Student student=new Student();
System.out.println("getByRollNumber got invoked:>>>>>>>>>>>>>>>>>>>");
try
{
String name="";
char gender='\0';
PreparedStatement preparedStatement=null;
preparedStatement=connection.prepareStatement("select * from student where rollNumber=?");
preparedStatement.setInt(1,rollNumber);
ResultSet resultSet=null;
resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new StudentException("Roll number do not exists");
}
rollNumber=resultSet.getInt("rollNumber");
name=resultSet.getString("name");
gender=resultSet.getString("gender").charAt(0);
student.setRollNumber(rollNumber);
student.setName(name);
student.setGender(gender);
preparedStatement.close();
resultSet.close();
connection.close();
System.out.println("From StudentService the value of student object is: rollNumber:"+rollNumber+" , Name:"+name+"  ,Gender:"+gender);
}catch(SQLException exception)
{
System.out.println("Exception in getByRollNumber:"+exception.getMessage());
}
System.out.println("Returned from getByRollNumber>>>>>>>>>>>>>>>>>>>>>>>>>>");
return student;
}

@Path("/getAll")
public List<Student> getAll() throws StudentException
{
System.out.println("getAll got invoked");
Student student=null;
LinkedList<Student> list=new LinkedList<>();
try
{
Statement statement;
statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select * from student");
while(resultSet.next())
{
student=new Student();
student.setRollNumber(resultSet.getInt("rollNumber"));
student.setName(resultSet.getString("name").trim());
student.setGender(resultSet.getString("gender").charAt(0));
list.add(student);
}
resultSet.close();
statement.close();
connection.close();
}catch(SQLException exception)
{
System.out.println(exception.getMessage());
}
System.out.println("return from getAll got invoked");
return list;
}
}
