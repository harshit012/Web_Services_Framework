# Web-Services-Framework
This is the java based Web Services Framework, which provides an easier and faster way to set up, configure, and create the backend/serverside of the web-application. Now the user do not need to worry about servlets or the configuration of web.xml anymore. 

## Why to use this framework?
* No need to write the code for servlet classes.
* The framework generates a javascript file, Which contains the necessary implementation of all the service classes at backend/serverside.
* No need to do url mappings in XML file. (deployment descriptor)
* User don't need to worry about GET/POST request and how to Handle them.
* The user can easily send/receive the data from client-side to server-side without writing the single line of code of JSON parsing.


## How to use this framework?
1 -> Download web_services.jar file.

2 -> Cut/Copy that jar file to tomcat9/webapps/'Project_Name'/WEB-INF/lib/;

3 -> Some following changes has to be performed in web.xml.

   * You need to specify param-value against param-name 'SERVICE_PACKAGE_PREFIX'. In my project it is 'bobby' as shown below.
    User just need to change/write a single word inside web.xml and that was the param-value against param-name 'SERVICE_PACKAGE_PREFIX' i.e. by default there was "bobby",           user have to change it.
    It is the package prefix like if package name is : "bobby.abc.def" then you need to write 'bobby'. 'bobby' is the name of a folder.

      Note : The SERVICE_PACKAGE_PREFIX mention here i.e. 'bobby' should exist inside tomcat9/webapps/"project name"/WEB-INF/classes/.

      The package consist of service classes in it (services classes are classes using the framework to create web service for requests).
      ```
      <init-param>
      <param-name>SERVICE_PACKAGE_PREFIX</param-name>
      <param-value>bobby</param-value>
      </init-param>
      ```

   * You have to change a single word inside web.xml, instead of 'schoolService' user have to write his/her application entity name there.
      ```
      <servlet>
      <servlet-name>TMWebRock</servlet-name>
      <servlet-class>com.thinking.machines.webrock.TMWebRock</servlet-class>
      </servlet>
      <servlet-mapping>
      <servlet-name>TMWebRock</servlet-name>
      <url-pattern>/schoolService/*</url-pattern>
      </servlet-mapping>
      ```
Now the web.xml has been configured.

4 -> Now copy all the necessary .jar files to tomcat9/webapps/"Project Name"/WEB-INF/lib/.

5 -> Now copy all files inside Dependencies folder and paste them inside tomcat9/lib/. these are all the files you will ever need to create a web service. Our framework is Dependent on some of the these files. some of the jar file may already be present there you can skip those files.

6 -> You are done setting up the environment,now you can use the frameWork easily.


# Tutorials and reference documentation:
## Annotations:
**1. @Path("/employee")**

   Path annotation can be applied on class and method. The value of this annotation should always starts with front Slash followed by path.

   Example:-
```
import com.thinking.machines.webrock.annotations.*;
@Path("/employee")
public class Employee
{
@Path("/add")
public void add()
{
System.out.println("Employee added");
}
}
```
   user can access this service by sending request to "User's entity name"/employee/view.
   
**2. @RequestParameter("username").**

   RequestParameter annotation can only be applied on Parameters. User is supposed to apply Request Parameter annotation on all the primitive types. User is not supposed to define a service which has more than one complex parameter type beacuse it may be ambigious when the request is of JSON type & user do not need to apply this annotation in complex data type. By this annotation the framework get to know that the service which is going to invoke is the correct one or not.
   
   Example:-
```
import com.thinking.machines.webrock.annotations.*;
@Path("/employee")
public class Employee
{
@Path("/add")
public String add(@RequestParameter("username") String name,@RequestParameter("gender") String gender,@RequestParameter("indian") boolean indian)
{
System.out.println(name+"----"+gender+"-----"+indian);
return "Successfully added";
}
}
```
Example url to access add service http://localhost:8080/"user's-application-context-name"/"user's-entity-name"/employee/add?username=Harshit+Choukse&gender=male&indian=true & then the add method got invoked with the appropriate arguments.

**3. @SecuredAccess(checkPost="bobby.Security.ValidateUser",guard="checkUser")**

   By using this annotation user dont have to write verification code for every service that need to be secured,user can just apply this annotation to all the services that are    needed to be secured from unidentified access. SecuredAccess annotation can only be applied on Service/Method.
   
   * checkPost = full classname(with package) to your verification class.
   * guard = method name of verification class.

Example:-
```
import com.thinking.machines.webrock.annotations.*;
@Path("/employee")
public class Employee
{
@Path("/get")
@SecuredAccess(checkPost="bobby.Security.ValidateUser",guard="checkUser")
public String getAadharCardNumber()
{
int aadhar=0;
//code to get adhar card in ;
return aadhar;
}
}
```

**4. @Forward("/employee/view")**

   Using this annotation user can forward request to another web service or to some client side technology like (jsp file/ html file) .The example below shows, How to use forward annotaton to forward request to other service "/employee/view",you can also forward to some JSP also.by giving JSP file name as value of forward annotation. Forward annotation can only be applied on Services(Method which has path annotation applied on it).
 
**5. @Get**

   By using this annotation user is declaring that only GET type request allowed for this service. Get annotation can be applied to both class and method. If this annotation is    applied on class then all the services inside that class can only accept GET type request. 
  
**6. @Post**

   Similarly as Get annotation, Post annotation can be used for allowing POST type request. and it can also applied on both class and method.

If neither Get nor Post annotation is applied on method then both GET and POST type requests allowed. 

Example :-
```
import com.thinking.machines.webrock.annotations.*;
@Path("/employee")
@Get
public class Employee
{
@Path("/add")
@Get
@Forward("/employee/view")
public String add(@RequestParameter("username") String name,@RequestParameter("gender") String gender,@RequestParameter("indian") boolean indian)
{
System.out.println(name+"----"+gender+"-----"+indian);
return "Add model service Used";
}

@Path("/view")
public void view()
{
System.out.println("View Service");
}
}
```

**Framework provides three classes:**
* RequestScope
* SessionScope
* ApplicationScope

If you want to use web application scopes . You can simply use these classes. All the above classes has two methods below methods.
*  void setAttribute(String key,Object value);
*  Object getAttribute(String key);

For all the three classes there are three annotations:

**7. @InjectApplicationScope**

**8. @InjectSessionScope**

**9. @InjectRequestScope**

Example

```
package bobby.test;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;
@InjectApplicationScope
public class ABCD
{
private Bulb bulb;
private ApplicationScope applicationScope;
@Path("/sam")
public void sam()
{
this.bulb=new Bulb();
this.bulb.setWattage(50);
this.applicationScope.setAttribute("bulb_data",this.bulb);
}
public void setApplicationScope(ApplicationScope applicationScope)
{
this.applicationScope=applicationScope;
}
}
```
The class ABCD requires application scope. For that, the user has to declare a field of type ApplicationScope along with the setter method as shown in the above code. Whenever there is a request arrived for sam, then before the invocation of sam service the setApplicationScope method got invoked.

Note: All this three class are in package : com.thinking.machines.webrock.pojo.*;

There is also a alternative approach to write that code. Instead of applying the InjectApplicationScope on that class you can simply introduce one more parameter in sam method of type Application Scope, As shown below in code.

```
package bobby.test;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;
public class ABCD
{
private Bulb bulb;
@Path("/sam")
public void sam(ApplicationScope applicationScope)
{
this.bulb=new Bulb();
this.bulb.setWattage(50);
applicationScope.setAttribute("bulb_data",this.bulb);
}
}
```

**Similarly, The code can be written for RequestScope & SessionScope**

**10.@InjectApplicationDirectory**

   If you wanted to get the working directory of your project then you have to apply this annotation on class. The class should contain a field of type ApplicationDirectory &      the necessary setter method. The way of writing code is as same as the above code.
   
The Class ApplicationDirectory has only one method:
* File getDirectory(); 

Example:
```
package bobby.test;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;
@InjectApplicationDirectory
public class ABCD
{
private Bulb bulb;
private ApplicationDirectory applicationDirectory;
@Path("/sam")
public void sam()
{
System.out.println("Current Directory:"+this.applicationDirectory.getRealPath());
}
public void setApplicationDirectory(ApplicationDirectory applicationDirectory)
{
this.applicationDirectory=applicationDirectory;
}
}
```
Or
```
package bobby.test;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;
public class ABCD
{
private Bulb bulb;
@Path("/sam")
public void sam(ApplicationDirectory applicationDirectory)
{
System.out.println("Current Directory:"+this.applicationDirectory.getRealPath());
}
}
```

**11. @InjectRequestParameter("gender")**

   This annotation is same as RequestParameter annotation but unlike it applied on class properties. It simply work similar as RequestParameter but benefit of using this            annotation was that if some data is arriving through query string and more than one service required that same data then instead of using applying RequestParameter annotation    on each services user can use InjectRequestParameter on that field, which is accessible to all services.
 
 Example:-
```
import com.thinking.machines.webrock.annotations.*;
@Path("/employee")
public class EmployeeManager
{
@InjectRequestParameter("empId")
private int empId;
@Path("/getByEmployeeId")
public Employee getByEmployeeId()
{
int empId=this.empId;
//code to search the employee in Database
Employee emp=new Employee;
return emp;
}
@Path("/delete")
public void delete()
{
int empId=this.empId;
//code to delete empId from database;
}
public void setEmpid(int empId)
{
this.empId=empId;
}
}
```
Here, In the above code whenever the request is arrived for any service in EmployeeManager class then At First, the data is extracted from query string & setEmpId got invoked & after that the appropriate service got invoked.

**12. @OnStartup(priority=1)**

   If you wanted to invoke the service when the server get started then you can apply this annotation on that service. This annotation can only be applied on method/service. If    user wants to invoke more than one service at startup of server then he should mention the priority of invocation of services. Service with lesser priority number will be        called first.

Example :-
```
package bobby.test;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;
public class ABCD
{
private Bulb bulb;
@Startup(PRIORITY=1)
public void sam(ApplicationScope applicationScope)
{
this.bulb=new Bulb();
this.bulb.setWattage(50);
applicationScope.setAttribute("bulb_data",this.bulb);
}
}
```
Note: You are not supposed to apply Path annotation with startup annotation. you cannot use RequestScope or SessionScope as a parameter. you can only use ApplicationScope as a parameter.

**13. @AutoWired(name="username")**

   AutoWired annotation can only be applied on properties of a service class. User apply these annotation when he/she wants that the value against 'name' property of AutoWired     annotation should setted to that property on which annotation applied and value will be extracted from scopes (request scope > session scope > application scope). Setter        method should be present for that property so that value can be setted.

i.e. order of finding the value against value of name field of annotation was - Request scope -> Session scope -> Application scope.

Example :-
```
import com.thinking.machines.webrock.annotations.*;
@Path("/employee")
public class Employee
{
@AutoWired(name="username")
private String name;
public void setName(String name)
{
this.name=name;
}
@Path("/get")
public String getName()
{
System.out.print("Name of employee: ");
System.out.println(this.name);
return this.name;
}
}
```
If the username key is exists in any of these scope then its value will be assigned to name attribute of Employee class.
___________________________________________________________________________________________________________

## Exceptions:

I have created some exceptions ,so that user get to know the faults in his/her Web-Application.


|       Exception       |                      Description                      |
| ----------------------| ----------------------------------------------------- |
|    SetterNotFound     | This Exception is raised when the definition of setter method is not found.|
|GaurdNotFound|When SecuredAccess Annotation is applied on any class and the method, act as a guard is not found.|
|PostNotFound|When SecuredAccess Annotation is applied on any class and the Class, act as a post is not found.|
|ServiceNotFound|It is thrown when accessing a service which is not found.|
|ServiceClassNotFound|This exception is raised when accessing a class having Path annotion on it, is not found|
|BaseURLPatternNotFound|It is thrown when base URL is not provided in web.xml.|
|InvalidMethodParameters|It is thrown when the number of parameters is out of range or the type of the parameters is not according to the specified guidelines.|
|InvalidServiceParameters|This is raised when the service does not follow the specified guidelines to declare parameter variables.|
|ServiceInvocation|This is thrown when the invoked service throws an exception.|
|IllegalServiceAccess|This is thrown when the access of method is not according to the specified guidelines.|
|ServiceClassInstantiation|This is raised when the class is not instantiable.|
|PathNotApplied|This is thrown to indicate that, The Path annotation must be applied on the service.|
|ServiceNotAllowed|This is thrown when the request type do not match.|
|PackagePrefixNotFound|This is raised when the package prefix is not mentioned in web.xml.|
|AmbigiousRequestType|"This is thrown when the GET & POST Annotation applied on same service."|
