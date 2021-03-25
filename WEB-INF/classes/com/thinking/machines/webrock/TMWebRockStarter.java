package com.thinking.machines.webrock;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.thinking.machines.webrock.model.*;
import com.thinking.machines.webrock.pojo.*;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.exceptions.*;
import java.lang.annotation.*;

import java.lang.reflect.*;


public class TMWebRockStarter extends HttpServlet
{
private WebRockModel model;
private List<Service> startupMethods;
private HashMap<Class,List<Method>> jsServiceClass;

public boolean isPrimitive(Class c)
{
String typeName;
typeName=c.getName();
if(typeName.equals("java.lang.Integer")|| typeName.equals("int")) return true;
if(typeName.equals("java.lang.String")|| typeName.equals("String")) return true;
if(typeName.equals("java.lang.Float")|| typeName.equals("float")) return true;
if(typeName.equals("java.lang.Boolean")|| typeName.equals("boolean")) return true;
if(typeName.equals("java.lang.Byte")|| typeName.equals("byte")) return true;
if(typeName.equals("java.lang.Short")|| typeName.equals("short")) return true;
if(typeName.equals("java.lang.Double")|| typeName.equals("double")) return true;
if(typeName.equals("java.lang.Char")|| typeName.equals("char")) return true;
if(typeName.equals("java.lang.Long")|| typeName.equals("long")) return true;
return false;
}


public void initializeApplicationDirectory(Class cls,Object obj) throws ServiceException
{
Method method=null;
ApplicationDirectory applicationDirectory=new ApplicationDirectory(new File(getServletContext().getRealPath("")));
try
{
method=cls.getDeclaredMethod("setApplicationDirectory",ApplicationDirectory.class);
}catch(NoSuchMethodException noSuceMethodException)
{
throw new ServiceException("SetterNotFound");
}
if(method!=null)
{
try
{
method.invoke(obj,applicationDirectory);
}catch(IllegalAccessException illegalAccessException)
{
throw new ServiceException("IllegalServiceAccess");
}
catch(IllegalArgumentException illegalArgumentException)
{
throw new ServiceException("InvalidMethodParameters");
}
catch(InvocationTargetException invocationTargetException)
{
throw new ServiceException("ServiceInvocation");
}
}
}


public void initializeApplicationScope(Class cls,Object obj) throws ServiceException
{
Method method=null;
ApplicationScope applicationScope=new ApplicationScope(getServletContext());
try
{
method=cls.getDeclaredMethod("setApplicationScope",ApplicationScope.class);
}catch(NoSuchMethodException noSuceMethodException)
{
throw new ServiceException("SetterNotFound");
}
if(method!=null)
{
try
{
method.invoke(obj,applicationScope);
}catch(IllegalAccessException illegalAccessException)
{
throw new ServiceException("IllegalServiceAccess");
}
catch(IllegalArgumentException illegalArgumentException)
{
throw new ServiceException("InvalidMethodParameters");
}
catch(InvocationTargetException invocationTargetException)
{
throw new ServiceException("ServiceInvocation");
}
}
}

public int hasMethod(String methodName,Method methods[])
{
int e=0;
for(Method method:methods)
{
if(methodName.equals(method.getName()))
{
return e;
}
e++;
}
return -1;
}

public boolean validateParameterTypes(Method method)
{
Class[] parameterTypes=method.getParameterTypes();
if(parameterTypes.length==0) return true;
for(Class parameterType:parameterTypes)
{
if(parameterType.getName().equals("com.thinking.machines.webrock.pojo.ApplicationScope")) continue;
else if(parameterType.getName().equals("com.thinking.machines.webrock.pojo.ApplicationDirectory")) continue;
else if(parameterType.getName().equals("com.thinking.machines.webrock.pojo.RequestScope")) continue;
else if(parameterType.getName().equals("com.thinking.machines.webrock.pojo.SessionScope"))continue;
else return false;
}
return true;
}


public void doProcessing(String file,int beginFrom) throws ServiceException
{
int len,value;
boolean isInjectSessionScope=false;
boolean isInjectApplicationScope=false;
boolean isInjectApplicationDirectory=false;
boolean isInjectRequestScope=false;
boolean isPathAnnotationAppliedOnClass=false;
boolean isPathAnnotationAppliedOnMethod=false;
boolean isStartupAnnotationApplied=false;
boolean isForwardAnnotationApplied=false;
boolean hasAutoWiredProperty=false;
boolean hasInjectRequestParameterAnnotation=false;
boolean hasSecuredAccessAnnotationApplied=false;

SecuredAccessInfo securedAccessInfo=null;
String requestType=null;
Service service=null;
Class cls=null;
Method methods[]=null;
Parameter parameters[]=null;
Field fields[]=null;
String classPathValue="";
String methodPathValue="";
String fullPath="";
String fieldName="";
String methodName="";
String nameKey="";
Path path=null;
int flag=-1;
List<AutoWiredProperty> autoWiredProperties=new LinkedList<>();
List<RequestParameterInfo> requestParameters=new LinkedList<>();


String packageName=file.replace("\\",".");
len=packageName.length();
packageName=packageName.substring(beginFrom,len-6);

try
{
cls=Class.forName(packageName);
}catch(ClassNotFoundException classNotFoundException)
{
throw new ServiceException("ServiceClassNotFound");
}
methods=cls.getDeclaredMethods();
fields=cls.getDeclaredFields();
for(Field field:fields)
{
if(field.isAnnotationPresent(AutoWired.class))
{
fieldName=field.getName();
methodName="set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
flag=hasMethod(methodName,methods);
if(flag!=-1)
{
Method method=methods[flag];
AutoWired autoWired=field.getAnnotation(AutoWired.class);
nameKey=autoWired.name();
hasAutoWiredProperty=true;
AutoWiredProperty autoWiredProperty=new AutoWiredProperty();
autoWiredProperty.setField(field);
autoWiredProperty.setMethod(method);
autoWiredProperty.setNameKey(nameKey);
autoWiredProperties.add(autoWiredProperty);
}
else
{
throw new ServiceException("SetterNotFound");
}
}

if(field.isAnnotationPresent(InjectRequestParameter.class))
{
fieldName=field.getName();
methodName="set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
flag=hasMethod(methodName,methods);
if(flag!=-1)
{
Method method=methods[flag];
InjectRequestParameter requestParameterAnnotation=field.getAnnotation(InjectRequestParameter.class);
nameKey=requestParameterAnnotation.value();
hasInjectRequestParameterAnnotation=true;
RequestParameterInfo requestParameter=new RequestParameterInfo();
requestParameter.setField(field);
requestParameter.setMethod(method);
requestParameter.setNameKey(nameKey);
requestParameters.add(requestParameter);
}
else
{
throw new ServiceException("SetterNotFound");
}
}
}//loop for traversing fields ends here


if(cls.isAnnotationPresent(Path.class))
{
isPathAnnotationAppliedOnClass=true;
this.jsServiceClass.put(cls,null);
}

if(cls.isAnnotationPresent(SecuredAccess.class))
{
Annotation annotation=cls.getAnnotation(SecuredAccess.class);
SecuredAccess securedAccess=(SecuredAccess)annotation;
String className=securedAccess.checkPost();
String mName=securedAccess.guard();
Class c=null;
try
{
c=Class.forName(className);
}catch(ClassNotFoundException classNotFoundException)
{
throw new ServiceException("PostNotFound");
}
Method m=null;
try
{
m=c.getDeclaredMethod(mName);
}catch(NoSuchMethodException noSuchMethodException)
{
throw new ServiceException("GaurdNotFound");
}
if(validateParameterTypes(m))
{
securedAccessInfo=new SecuredAccessInfo();
securedAccessInfo.setClassName(c);
securedAccessInfo.setMethod(m);
hasSecuredAccessAnnotationApplied=true;
}
else throw new ServiceException("InvalidMethodParameters");
}
if(cls.isAnnotationPresent(GET.class) && cls.isAnnotationPresent(POST.class)) throw new ServiceException("AmbigiousRequestType");
if(cls.isAnnotationPresent(GET.class)) requestType="GET";
if(cls.isAnnotationPresent(POST.class)) requestType="POST";
if(cls.isAnnotationPresent(InjectSessionScope.class)) isInjectSessionScope=true;
if(cls.isAnnotationPresent(InjectRequestScope.class)) isInjectRequestScope=true;
if(cls.isAnnotationPresent(InjectApplicationScope.class)) isInjectApplicationScope=true;
if(cls.isAnnotationPresent(InjectApplicationDirectory.class)) isInjectApplicationDirectory=true;
if(isPathAnnotationAppliedOnClass)
{
Annotation annotation=cls.getAnnotation(Path.class);
path=(Path)annotation;
classPathValue=path.value();
}
for(Method method:methods)
{
service=new Service();
service.setServiceClass(cls);
service.setService(method);
if(method.isAnnotationPresent(GET.class)) requestType="GET";
if(method.isAnnotationPresent(POST.class)) requestType="POST";
if(isPathAnnotationAppliedOnClass && method.isAnnotationPresent(Path.class)) isPathAnnotationAppliedOnMethod=true;
if(method.isAnnotationPresent(Forward.class)) isForwardAnnotationApplied=true;
if(method.isAnnotationPresent(Startup.class)) isStartupAnnotationApplied=true;
if(isInjectSessionScope) service.injectSessionScope(true);
if(isInjectRequestScope) service.injectRequestScope(true);
if(isInjectApplicationScope) service.injectApplicationScope(true);
if(isInjectApplicationDirectory) service.injectApplicationDirectory(true);
if(hasAutoWiredProperty) service.setAutoWiredProperties(autoWiredProperties);
if(hasInjectRequestParameterAnnotation) service.setRequestParameters(requestParameters);
if(hasSecuredAccessAnnotationApplied) service.setSecuredAccess(securedAccessInfo);
else
{
if(method.isAnnotationPresent(SecuredAccess.class))
{
Annotation annotation=method.getAnnotation(SecuredAccess.class);
SecuredAccess securedAccess=(SecuredAccess)annotation;
String className=securedAccess.checkPost();
String mName=securedAccess.guard();
Class c=null;
try
{
c=Class.forName(className);
}catch(ClassNotFoundException classNotFoundException)
{
throw new ServiceException("ServiceClassNotFound");
}
Method m=null;
try
{
m=c.getDeclaredMethod(mName);
}catch(NoSuchMethodException noSuchMethodException)
{
throw new ServiceException("GaurdNotFound");
}
if(validateParameterTypes(method))
{
securedAccessInfo=new SecuredAccessInfo();
securedAccessInfo.setClassName(c);
securedAccessInfo.setMethod(m);
service.setSecuredAccess(securedAccessInfo);
}
else throw new ServiceException("InvalidMethodParameters");
}
}//else ends here
service.setType(requestType);
if(isPathAnnotationAppliedOnMethod==true && isStartupAnnotationApplied==false)
{
parameters=method.getParameters();
int primitiveCount=0,nonPrimitiveCount=0;
flag=0;
int count=0;
for(Parameter parameter:parameters)
{
count++;
if(parameter.isAnnotationPresent(RequestParameter.class))
{
if(isPrimitive(parameter.getType())) primitiveCount++;
else nonPrimitiveCount++;
continue;
}
else if(parameter.getType().getName().equals("com.thinking.machines.webrock.pojo.ApplicationScope")) continue;
else if(parameter.getType().getName().equals("com.thinking.machines.webrock.pojo.ApplicationDirectory")) continue;
else if(parameter.getType().getName().equals("com.thinking.machines.webrock.pojo.RequestScope")) continue;
else if(parameter.getType().getName().equals("com.thinking.machines.webrock.pojo.SessionScope")) continue;
else
{
if(!(isPrimitive(parameter.getType())))
{
nonPrimitiveCount++;
continue;
}
flag=1;
break;
}
}//loop ends here
if(nonPrimitiveCount>1) flag=1;
if(nonPrimitiveCount==1 && primitiveCount>0) flag=1;
if(flag==0 || (flag==1 && parameters.length==1))
{
path=method.getAnnotation(Path.class);
methodPathValue=path.value();
fullPath=classPathValue+methodPathValue;
service.setPath(fullPath);
if(method.isAnnotationPresent(Forward.class))
{
Forward forwardTo=method.getAnnotation(Forward.class);
service.setForwardTo(forwardTo.value());
}
this.model.add(fullPath,service);
if(this.jsServiceClass.get(cls)==null)
{
List<Method> listOfMethods=new LinkedList<>();
listOfMethods.add(method);
this.jsServiceClass.put(cls,listOfMethods);
}else
{
this.jsServiceClass.get(cls).add(method);
}
}
else throw new ServiceException("InvalidServiceParameters");
}

if(isStartupAnnotationApplied==true && isPathAnnotationAppliedOnMethod==false)
{
Startup startup=method.getAnnotation(Startup.class);
value=(int)startup.PRIORITY();
service.setPriority(value);
this.startupMethods.add(service);
}
if(isStartupAnnotationApplied==true && isPathAnnotationAppliedOnMethod==true) throw new ServiceException("PathNotApplied");
isPathAnnotationAppliedOnMethod=false;
isStartupAnnotationApplied=false;
isForwardAnnotationApplied=false;
}//loop ends here
}

private void startProcessing(File[] arr,int index,int length)  throws ServiceException
{
String absPath="";
if(index == arr.length) 
return; 
if(arr[index].isFile()) 
{
absPath=arr[index].getAbsolutePath();
if(absPath.endsWith(".class")) doProcessing(absPath,length);
}
else if(arr[index].isDirectory()) 
{ 
startProcessing(arr[index].listFiles(), 0,length); 
} 
startProcessing(arr,++index,length); 
} 

public void invokeStartupMethods() throws ServiceException
{
Object obj=null;
Collections.sort(this.startupMethods,new Comparator<Service>(){
public int compare(Service left,Service right)
{
return (left.getPriority())-(right.getPriority());
}
});
for(Service service:this.startupMethods)
{
try
{
obj=service.getServiceClass().newInstance();
}catch(InstantiationException istantiationException)
{
throw new ServiceException("ServiceClassInstantiation");
}
catch(IllegalAccessException illegalAccessException)
{
throw new ServiceException("IllegalServiceAccess");
}
if(service.injectApplicationScope()) initializeApplicationScope(service.getServiceClass(),obj);
if(service.injectApplicationDirectory()) initializeApplicationDirectory(service.getServiceClass(),obj);
Method method=service.getService();
try
{
method.invoke(obj);
}catch(IllegalAccessException illegalAccessException)
{
throw new ServiceException("IllegalServiceAccess");
}
catch(IllegalArgumentException illegalArgumentException)
{
throw new ServiceException("InvalidMethodParameters");
}
catch(InvocationTargetException invocationTargetException)
{
throw new ServiceException("ServiceInvocation");
}
}//for loop ends here
}

public boolean isPojo(Class c)
{
String typeName;
typeName=c.getName();
if(typeName.equals("java.lang.Integer")|| typeName.equals("int")) return false;
if(typeName.equals("java.lang.String")|| typeName.equals("String")) return false;
if(typeName.equals("java.lang.Float")|| typeName.equals("float")) return false;
if(typeName.equals("java.lang.Boolean")|| typeName.equals("boolean")) return false;
if(typeName.equals("java.lang.Byte")|| typeName.equals("byte")) return false;
if(typeName.equals("java.lang.Short")|| typeName.equals("short")) return false;
if(typeName.equals("java.lang.Double")|| typeName.equals("double")) return false;
if(typeName.equals("java.lang.Char")|| typeName.equals("char")) return false;
if(typeName.equals("java.lang.Long")|| typeName.equals("long")) return false;
if(typeName.equals("com.thinking.machines.webrock.pojo.ApplicationScope")) return false;
if(typeName.equals("com.thinking.machines.webrock.pojo.ApplicationDirectory")) return false;
if(typeName.equals("com.thinking.machines.webrock.pojo.RequestScope")) return false;
if(typeName.equals("com.thinking.machines.webrock.pojo.SessionScope")) return false;
return true;
}

public String getDefaultValue(Class c)
{
String typeName;
typeName=c.getName();
if(typeName.equals("java.lang.Integer")|| typeName.equals("int")) return "0";
if(typeName.equals("java.lang.String")|| typeName.equals("String")) return "\"\"";
if(typeName.equals("java.lang.Float")|| typeName.equals("float")) return "0";
if(typeName.equals("java.lang.Boolean")|| typeName.equals("boolean")) return "false";
if(typeName.equals("java.lang.Byte")|| typeName.equals("byte")) return "0";
if(typeName.equals("java.lang.Short")|| typeName.equals("short")) return "0";
if(typeName.equals("java.lang.Double")|| typeName.equals("double")) return "0";
if(typeName.equals("java.lang.Char")|| typeName.equals("char")) return "'\\0'";
if(typeName.equals("java.lang.Long")|| typeName.equals("long")) return "0";
return "null";
}

public boolean isScope(Class c)
{
String typeName;
typeName=c.getName();
if(typeName.equals("com.thinking.machines.webrock.pojo.ApplicationScope")) return true;
if(typeName.equals("com.thinking.machines.webrock.pojo.ApplicationDirectory")) return true;
if(typeName.equals("com.thinking.machines.webrock.pojo.RequestScope")) return true;
if(typeName.equals("com.thinking.machines.webrock.pojo.SessionScope")) return true;
return false;
}

public String getMethodURL(Method method,Class cls)
{
String url="";
Path path;
Annotation annotation=cls.getAnnotation(Path.class);
path=(Path)annotation;
url=path.value();
path=method.getAnnotation(Path.class);
url=url+path.value();
return url;
}


public  boolean isGetAnnotationApplied(Method method)
{
return method.isAnnotationPresent(GET.class);
}

public  boolean isPostAnnotationApplied(Method method)
{
return method.isAnnotationPresent(POST.class);
}

public String getValueFromRequestParameter(Parameter parameter)
{
String val="";
Annotation[] annotations=parameter.getAnnotations();
for(int e=0;e<annotations.length;e++)
{
RequestParameter requestParameter=(RequestParameter)annotations[0];
val=requestParameter.value();
}
return val;
}

public void createJSFile(String fileName,String urlPattern)
{
if(urlPattern==null) return;
int count=0;
int cnt=0;
boolean oneJS=false;
boolean hasPojo=false;
boolean isPojo=false;
boolean isPrimitive=false;
boolean isScope=false;
List<Parameter> actualParametersOfMethod=new LinkedList<>();
Set<Class> pojoClasses=new HashSet<>();
String folderPath=getServletContext().getRealPath("")+"WEB-INF\\js\\";
if(fileName!=null) oneJS=true;
if(oneJS) fileName=folderPath+fileName;
File file=new File(folderPath);
RandomAccessFile randomAccessFile=null;
if(!file.exists())
{
file.mkdirs();
}
try
{
if(oneJS)
{
file=new File(fileName);
if(file.exists()) file.delete();
randomAccessFile=new RandomAccessFile(file,"rw");
}
for(Map.Entry<Class,List<Method>> entry:this.jsServiceClass.entrySet())
{
if(!oneJS)
{
fileName=folderPath+entry.getKey().getSimpleName()+".js";
file=new File(fileName);
if(file.exists()) file.delete();
randomAccessFile=new RandomAccessFile(file,"rw");
}
randomAccessFile.writeBytes("class "+entry.getKey().getSimpleName()+"\r\n{\r\n"); //class body starts here
List<Method> lst=entry.getValue();
for(Method method:lst)
{
hasPojo=false;
randomAccessFile.writeBytes(method.getName()+"(");
Parameter[] parameters=method.getParameters();
count=0;
for(Parameter parameter:parameters)
{
Class type=parameter.getType();
if(isPojo(type))
{
if(!pojoClasses.contains(parameter.getType())) pojoClasses.add(parameter.getType());
isPojo=true;
hasPojo=true;
}
else if(isScope(type)) isScope=true;
else isPrimitive=true;
if(isPojo||isPrimitive)
{
if(count!=0) randomAccessFile.writeBytes(",");
randomAccessFile.writeBytes(parameter.getName());
actualParametersOfMethod.add(parameter);
count++;
}
isPojo=false;
isPrimitive=false;
isScope=false;
}
randomAccessFile.writeBytes(")\r\n{\r\n");
//method body starts here
if(hasPojo) randomAccessFile.writeBytes("var jsonString=JSON.stringify("+parameters[0].getName()+");\r\n");
randomAccessFile.writeBytes("var prm=new Promise(function(resolve,reject)\r\n");
randomAccessFile.writeBytes("{\r\n");
randomAccessFile.writeBytes("var xmlHttpRequest=new XMLHttpRequest();\r\n");
randomAccessFile.writeBytes("xmlHttpRequest.onreadystatechange=function(){\r\n");
randomAccessFile.writeBytes("if(this.readyState==4)\r\n");
randomAccessFile.writeBytes("{\r\n");
randomAccessFile.writeBytes("if(this.status==200)\r\n");
randomAccessFile.writeBytes("{\r\n");
randomAccessFile.writeBytes("var responseData=this.responseText;\r\n");
randomAccessFile.writeBytes("var dataObj=JSON.parse(responseData);\r\n");
randomAccessFile.writeBytes("resolve(dataObj);\r\n");
randomAccessFile.writeBytes("}\r\n");
randomAccessFile.writeBytes("else\r\n");
randomAccessFile.writeBytes("{\r\n");
randomAccessFile.writeBytes("reject(this.responseText);\r\n");
randomAccessFile.writeBytes("}\r\n");
randomAccessFile.writeBytes("}\r\n");
randomAccessFile.writeBytes("};\r\n");
if(hasPojo)
{
randomAccessFile.writeBytes("xmlHttpRequest.open('POST','"+urlPattern+getMethodURL(method,entry.getKey())+"',true);\r\n");
randomAccessFile.writeBytes("xmlHttpRequest.setRequestHeader(\"Content-type\",\"application/json\");\r\n");
randomAccessFile.writeBytes("xmlHttpRequest.send(jsonString);\r\n");
}else
{
randomAccessFile.writeBytes("var queryString=\"\";\r\n");
cnt=0;
for(Parameter parameter:actualParametersOfMethod)
{
if(cnt!=0)
{
randomAccessFile.writeBytes("queryString=queryString+\"&\";\r\n");
}
randomAccessFile.writeBytes("queryString=queryString+\""+getValueFromRequestParameter(parameter)+"\";\r\n");
randomAccessFile.writeBytes("queryString=queryString+\"=\";\r\n");
randomAccessFile.writeBytes("queryString=queryString+encodeURI(");
randomAccessFile.writeBytes(parameter.getName());
randomAccessFile.writeBytes(");\r\n");
cnt++;
}
if(isPostAnnotationApplied(method))
{
randomAccessFile.writeBytes("xmlHttpRequest.open('POST','"+urlPattern+getMethodURL(method,entry.getKey())+"',true);\r\n");
randomAccessFile.writeBytes("xmlHttpRequest.setRequestHeader(\"Content-type\",\"application/x-www-form-urlencoded\");\r\n");
randomAccessFile.writeBytes("xmlHttpRequest.send(queryString);\r\n");
}
else
{
randomAccessFile.writeBytes("var requestURL='"+urlPattern+getMethodURL(method,entry.getKey())+"' +'?'+queryString;\r\n");
randomAccessFile.writeBytes("xmlHttpRequest.open('GET',requestURL,true);\r\n");
randomAccessFile.writeBytes("xmlHttpRequest.send();\r\n");
}
}
randomAccessFile.writeBytes("});\r\n");
randomAccessFile.writeBytes("return prm;\r\n");
actualParametersOfMethod.clear();
randomAccessFile.writeBytes("}\r\n");  //method body ends here
}
randomAccessFile.writeBytes("}\r\n\r\n"); //class body ends here
if(!oneJS)
{
randomAccessFile.close();
}
}// all classes have been generated & loop ends here

Class pojoClass=null;
Field[] fields=null;
Iterator<Class> iter=pojoClasses.iterator();
while(iter.hasNext())
{
pojoClass=iter.next();
if(!oneJS)
{
fileName=folderPath+pojoClass.getSimpleName()+".js";
file=new File(fileName);
if(file.exists()) file.delete();
randomAccessFile=new RandomAccessFile(file,"rw");
}
fields=pojoClass.getDeclaredFields();
randomAccessFile.writeBytes("class "+pojoClass.getSimpleName()+"\r\n{\r\n"); //class body starts here
randomAccessFile.writeBytes("constructor()\r\n");
randomAccessFile.writeBytes("{\r\n");
for(Field field:fields)
{
randomAccessFile.writeBytes("this.");
randomAccessFile.writeBytes(field.getName());
randomAccessFile.writeBytes("=");
randomAccessFile.writeBytes(getDefaultValue(field.getType())+";\r\n");
}
randomAccessFile.writeBytes("}\r\n");
randomAccessFile.writeBytes("}\r\n\r\n"); //class body ends here
if(!oneJS)
{
randomAccessFile.close();
}
}//loop ends here
if(file!=null) randomAccessFile.close();
}catch(IOException ioException)
{
System.out.println("IOException:"+ioException);
}
}

public void init()
{
try
{
int len;
ServletContext context=getServletContext();
ServletConfig config=getServletConfig();
this.model=new WebRockModel();
this.startupMethods=new LinkedList<>();
this.jsServiceClass=new HashMap<>();
String packageNamePrefix=config.getInitParameter("SERVICE_PACKAGE_PREFIX");
String jsFileName=config.getInitParameter("JS_FILE_NAME");
String urlPattern=config.getInitParameter("BASE_URL_PATTERN");
if(urlPattern!=null) urlPattern=urlPattern.substring(1);
String path=context.getRealPath("");
path=path+"WEB-INF\\classes\\";
len=path.length();
if(packageNamePrefix!=null)
{
if(packageNamePrefix.length()==0) throw new ServiceException("PackagePrefixNotFound");
path=path+packageNamePrefix;
File file=new File(path);
if(!file.exists()) throw new ServiceException("PackagePrefixNotFound");
startProcessing(new File(path).listFiles(),0,len);
}
else throw new ServiceException("PackagePrefixNotFound");
if(jsFileName!=null && urlPattern==null) throw new ServiceException("BaseURLPatternNotFound");
createJSFile(jsFileName,urlPattern);
invokeStartupMethods();
getServletContext().setAttribute("model",this.model);
}catch(ServiceException serviceException)
{
System.out.println("ServiceException:"+serviceException.getMessage());
}
catch(Exception exception)
{
System.out.println(exception);
}
}
}