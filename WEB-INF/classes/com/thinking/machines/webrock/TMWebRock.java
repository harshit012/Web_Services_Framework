package com.thinking.machines.webrock;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.thinking.machines.webrock.model.*;
import com.thinking.machines.webrock.pojo.*;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.exceptions.*;
import java.lang.reflect.*;
import java.util.*;
import com.google.gson.*;

public class TMWebRock extends HttpServlet
{
public String requestSplitter(String req)
{
int e=0;
e=req.indexOf("/",e);
e=req.indexOf("/",e+1);
e=req.indexOf("/",e+1);
return req.substring(e);
}

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


public static Class correctParameterType(Class c) throws ServiceException
{
String typeName;
typeName=c.getName();
try
{
if(typeName.equals("int")) c=Class.forName("java.lang.Integer");
else if(typeName.equals("float")) c=Class.forName("java.lang.Float");
else if(typeName.equals("boolean")) c=Class.forName("java.lang.Boolean");
else if(typeName.equals("byte")) c=Class.forName("java.lang.Byte");
else if(typeName.equals("short")) c=Class.forName("java.lang.Short");
else if(typeName.equals("double")) c=Class.forName("java.lang.Double");
else if(typeName.equals("char")) c=Class.forName("java.lang.Char");
else if(typeName.equals("long")) c=Class.forName("java.lang.Long");	
else return c;
}
catch(ClassNotFoundException exception)
{
throw new ServiceException("ClassNotFound");
}
return c;
}

private Object[] getArgumentsOfGuard(Method guard,ApplicationScope applicationScope,ApplicationDirectory applicationDirectory,SessionScope sessionScope,RequestScope requestScope)
{
Parameter[] parameters=guard.getParameters();
Object arguments[]=new Object[parameters.length];
int count=0;
for(Parameter parameter:parameters)
{
if(parameter.getType().getName().equals("com.thinking.machines.webrock.pojo.ApplicationScope")) arguments[count++]=applicationScope;
else if(parameter.getType().getName().equals("com.thinking.machines.webrock.pojo.ApplicationDirectory")) arguments[count++]=applicationDirectory;
else if(parameter.getType().getName().equals("com.thinking.machines.webrock.pojo.RequestScope")) arguments[count++]=requestScope;
else if(parameter.getType().getName().equals("com.thinking.machines.webrock.pojo.SessionScope")) arguments[count++]=sessionScope;
else return null;
}
return arguments;
}

public boolean checkForSecuredAccess(SecuredAccessInfo securedAccessInfo,HttpServletRequest request) throws ServiceException
{
Object obj=null;
RequestScope requestScope=new RequestScope(request);
SessionScope sessionScope=new SessionScope(request.getSession());
ApplicationScope applicationScope=new ApplicationScope(getServletContext());
ApplicationDirectory applicationDirectory=new ApplicationDirectory(new File(getServletContext().getRealPath("")));
Class className=securedAccessInfo.getClassName();
Method guard=securedAccessInfo.getMethod();
Method setter=null;
try
{
obj=className.newInstance();
}catch(InstantiationException istantiationException)
{
throw new ServiceException("ServiceClassInstantiation");
}
catch(IllegalAccessException illegalAccessException)
{
throw new ServiceException("IllegalServiceAccess");
}
if(securedAccessInfo.hasInjectApplicationScopeAnnotation())
{
try
{
setter=className.getDeclaredMethod("setApplicationScope",ApplicationScope.class);
}catch(NoSuchMethodException noSuceMethodException)
{
throw new ServiceException("SetterNotFound");
}
try
{
setter.invoke(obj,applicationScope);
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
if(securedAccessInfo.hasInjectApplicationDirectoryAnnotation())
{
try
{
setter=className.getDeclaredMethod("setApplicationDirectory",ApplicationDirectory.class);
}catch(NoSuchMethodException noSuceMethodException)
{
throw new ServiceException("SetterNotFound");
}
try
{
setter.invoke(obj,applicationDirectory);
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
if(securedAccessInfo.hasInjectSessionScopeAnnotation())
{
try
{
setter=className.getDeclaredMethod("setSessionScope",SessionScope.class);
}catch(NoSuchMethodException noSuceMethodException)
{
throw new ServiceException("SetterNotFound");
}
try
{
setter.invoke(obj,sessionScope);
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
if(securedAccessInfo.hasInjectRequestScopeAnnotation())
{
try
{
setter=className.getDeclaredMethod("setRequestScope",RequestScope.class);
}catch(NoSuchMethodException noSuceMethodException)
{
throw new ServiceException("SetterNotFound");
}
try
{
setter.invoke(obj,requestScope);
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
Object arguments[]=getArgumentsOfGuard(guard,applicationScope,applicationDirectory,sessionScope,requestScope);
if(arguments==null) return false;
try
{
guard.invoke(obj,arguments);
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
return true;
}


public Object parseValue(Class cls,String value) throws ServiceException
{
Object obj=null;
String typeName=cls.getName();
if(typeName.equals("java.lang.Integer")|| typeName.equals("int")) obj=Integer.parseInt(value.trim());
else if(typeName.equals("java.lang.String")|| typeName.equals("String")) obj=value;
else if(typeName.equals("java.lang.Float")|| typeName.equals("float")) obj=Float.parseFloat(value);
else if(typeName.equals("java.lang.Boolean")|| typeName.equals("boolean")) obj=Boolean.parseBoolean(value);
else if(typeName.equals("java.lang.Byte")|| typeName.equals("byte")) obj=Byte.parseByte(value);
else if(typeName.equals("java.lang.Short")|| typeName.equals("short")) obj=Short.parseShort(value);
else if(typeName.equals("java.lang.Double")|| typeName.equals("double")) obj=Double.parseDouble(value);
else if(typeName.equals("java.lang.Character")|| typeName.equals("char")) obj=value.charAt(0);
else if(typeName.equals("java.lang.Long")|| typeName.equals("long")) obj=Long.parseLong(value);
else obj=value;
return obj;
}


public Object[] getArguments(HttpServletRequest request,HttpServletResponse response,Service service) throws ServiceException
{
String contentType=request.getContentType();
Object arguments[]=new Object[service.getSizeOfParameters()];
Object argument=null;
String valueObtained="";
BufferedReader bufferedReader=null;
int count=0;
RequestParameter requestParameter=null;
ApplicationScope applicationScope=new ApplicationScope(getServletContext());
ApplicationDirectory applicationDirectory=new ApplicationDirectory(new File(getServletContext().getRealPath("")));
SessionScope sessionScope=new SessionScope(request.getSession());
RequestScope requestScope=new RequestScope(request);
try
{
Gson gson=new Gson();
for(Parameter parameter:service.getService().getParameters())
{
if(parameter.isAnnotationPresent(RequestParameter.class))
{
if(contentType!=null && contentType.indexOf("application/json")!=-1)
{
bufferedReader=request.getReader();
String tempData;
StringBuffer stringBuffer=new StringBuffer();
while(true)
{
tempData=bufferedReader.readLine();
if(tempData==null) break;
stringBuffer.append(tempData);
}
String rawData=stringBuffer.toString();
Object object=gson.fromJson(rawData,parameter.getType());
arguments[count++]=object;
}
else
{
requestParameter=(RequestParameter)parameter.getAnnotation(RequestParameter.class);
valueObtained=request.getParameter(requestParameter.value());
if(valueObtained==null) return null;
argument=parseValue(parameter.getType(),valueObtained);
if(argument==null) return null;
arguments[count++]=argument;
}
}
else if(parameter.getType().getName().equals("com.thinking.machines.webrock.pojo.ApplicationScope")) arguments[count++]=applicationScope;
else if(parameter.getType().getName().equals("com.thinking.machines.webrock.pojo.ApplicationDirectory")) arguments[count++]=applicationDirectory;
else if(parameter.getType().getName().equals("com.thinking.machines.webrock.pojo.RequestScope")) arguments[count++]=requestScope;
else if(parameter.getType().getName().equals("com.thinking.machines.webrock.pojo.SessionScope")) arguments[count++]=sessionScope;
else
{
if(contentType!=null && contentType.indexOf("application/json")!=-1)
{
bufferedReader=request.getReader();
String tempData;
StringBuffer stringBuffer=new StringBuffer();
while(true)
{
tempData=bufferedReader.readLine();
if(tempData==null) break;
stringBuffer.append(tempData);
}
String rawData=stringBuffer.toString();
Object object=gson.fromJson(rawData,parameter.getType());
arguments[count++]=object;
continue;
}
return null;
}
}
}
catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
return arguments;
}

public boolean isClientSideTechnology(String forwardTo)
{
if(forwardTo.indexOf(".")==-1) return false;
return true;
}



public void initializeAutoWiredProperties(HttpServletRequest request,HttpServletResponse response,Service service,Object obj) throws ServiceException
{
HttpSession session=request.getSession();
ServletContext servletContext=getServletContext();
List<AutoWiredProperty> list=service.getAutoWiredProperties();
if(list==null) return;
String name="";
Class cls=null;
Object temp;
Method method;
for(AutoWiredProperty autoWiredProperty:list)
{
if(autoWiredProperty.getSizeOfParameters()!=1) continue;
cls=autoWiredProperty.getFirstParameter();
method=autoWiredProperty.getMethod();
name=autoWiredProperty.getNameKey();
temp=request.getAttribute(name);
if(temp!=null)
{
if(!(cls.isInstance(temp))) continue;
try
{
method.invoke(obj,cls.cast(temp));
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
temp=null;
continue;
}
temp=session.getAttribute(name);
if(temp!=null)
{
if(!(cls.isInstance(temp))) continue;
try
{
method.invoke(obj,cls.cast(temp));
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
temp=null;
continue;
}
temp=servletContext.getAttribute(name);
if(temp!=null)
{
if(!(cls.isInstance(temp))) continue;
try
{
method.invoke(obj,cls.cast(temp));
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
temp=null;
continue;
}
temp=null;
}
}


public void initializeRequestParameters(HttpServletRequest request,HttpServletResponse response,Service service,Object obj) throws ServiceException
{
List<RequestParameterInfo> list=service.getRequestParameters();
if(list==null) return;
String name="";
Class cls=null;
String temp;
Method method;
for(RequestParameterInfo requestParameter:list)
{
if(requestParameter.getSizeOfParameters()!=1) continue;
cls=requestParameter.getFirstParameter();
method=requestParameter.getMethod();
name=requestParameter.getNameKey();
temp=request.getParameter(name);
if(temp!=null)
{
try
{
method.invoke(obj,parseValue(cls,temp));
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
}

public void initializeScope(HttpServletRequest request,HttpServletResponse response,Service service,Object obj) throws ServiceException
{
HttpSession session=request.getSession();
ServletContext servletContext=getServletContext();
Method method=null;
if(service.injectApplicationScope())
{
ApplicationScope applicationScope=new ApplicationScope(servletContext);
try
{
method=service.getServiceClass().getDeclaredMethod("setApplicationScope",ApplicationScope.class);
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
if(service.injectApplicationDirectory())
{
ApplicationDirectory applicationDirectory=new ApplicationDirectory(new File(getServletContext().getRealPath("")));
try
{
method=service.getServiceClass().getDeclaredMethod("setApplicationDirectory",ApplicationDirectory.class);
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

if(service.injectSessionScope())
{
SessionScope sessionScope=new SessionScope(session);
try
{
method=service.getServiceClass().getDeclaredMethod("setSessionScope",SessionScope.class);
}catch(NoSuchMethodException noSuceMethodException)
{
throw new ServiceException("SetterNotFound");
}
if(method!=null) 
{
try
{
method.invoke(obj,sessionScope);
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
if(service.injectRequestScope())
{
RequestScope requestScope=new RequestScope(request);
try
{
method=service.getServiceClass().getDeclaredMethod("setRequestScope",RequestScope.class);
}catch(NoSuchMethodException noSuceMethodException)
{
throw new ServiceException("SetterNotFound");
}
if(method!=null)
{
try
{
method.invoke(obj,requestScope);
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
}


public void sendBackReturnedData(Object returnedData,Gson gson,PrintWriter pw)
{
if(returnedData!=null)
{
if(isPrimitive(returnedData.getClass()))
{
pw.println(returnedData);
pw.flush();
}
else
{
ServiceResponse serviceResponse=new ServiceResponse();
serviceResponse.setResult(returnedData);
serviceResponse.setException(null);
serviceResponse.isSuccessful(true);
String jsonString=gson.toJson(serviceResponse);
pw.println(jsonString); 
pw.flush();
}
}
else
{
ServiceResponse serviceResponse=new ServiceResponse();
serviceResponse.setException(null);
serviceResponse.setResult(null);
serviceResponse.isSuccessful(true);
String jsonString=gson.toJson(serviceResponse);
pw.println(jsonString); 
pw.flush();
}
}


public void doProcessing(HttpServletRequest request,HttpServletResponse response,String requestType)
{
try
{
Object obj=null;
Object arguments[]=null;
Gson gson=new Gson();
PrintWriter pw=response.getWriter();
ServletConfig config=getServletConfig();
ServletContext context=getServletContext();
String req=requestSplitter(request.getRequestURI());
WebRockModel model=(WebRockModel)context.getAttribute("model");
Service service=(Service)model.get(req);
if(service==null) 
{
response.sendError(404);
throw new ServiceException("ServiceNotFound");
}
SecuredAccessInfo securedAccessInfo=service.getSecuredAccess();
if(securedAccessInfo!=null && !(checkForSecuredAccess(securedAccessInfo,request)))
{
response.sendError(404);
return;
}
String serviceRequestType=service.getType();
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

Method method=service.getService();
Object returnedData=null;
if(serviceRequestType==null || serviceRequestType.equalsIgnoreCase(requestType))
{
initializeScope(request,response,service,obj);
initializeRequestParameters(request,response,service,obj);
initializeAutoWiredProperties(request,response,service,obj);
if(service.getSizeOfParameters()==0)
{
try
{
returnedData=method.invoke(obj);
if(service.getForwardTo()==null)
{
sendBackReturnedData(returnedData,gson,pw);
return;
}
}catch(InvocationTargetException invocationTargetException)
{
Object ob=invocationTargetException.getTargetException();
ServiceResponse serviceResponse=new ServiceResponse();
serviceResponse.setException(ob);
serviceResponse.setResult(null);
serviceResponse.isSuccessful(false);
String jsonString=gson.toJson(serviceResponse);
pw.println(jsonString); 
pw.flush();
return;
}
catch(IllegalAccessException illegalAccessException)
{
throw new ServiceException("IllegalServiceAccess");
}
catch(IllegalArgumentException illegalArgumentException)
{
throw new ServiceException("InvalidMethodParameters");
}
}//if ends here
else
{
arguments=getArguments(request,response,service);
if(arguments==null)
{
response.sendError(500);
throw new ServiceException("ServiceNotFound");
}
try
{
returnedData=method.invoke(obj,arguments);
if(service.getForwardTo()==null)
{
sendBackReturnedData(returnedData,gson,pw);
return;
}
}catch(InvocationTargetException invocationTargetException)
{
Object ob=invocationTargetException.getTargetException();
ServiceResponse serviceResponse=new ServiceResponse();
serviceResponse.setException(ob);
serviceResponse.setResult(null);
serviceResponse.isSuccessful(false);
String jsonString=gson.toJson(serviceResponse);
pw.println(jsonString); 
pw.flush();
return;
}catch(IllegalAccessException illegalAccessException)
{
throw new ServiceException("IllegalServiceAccess");
}
catch(IllegalArgumentException illegalArgumentException)
{
throw new ServiceException("InvalidMethodParameters");
}
}
}
else
{
response.sendError(405);
throw new ServiceException("ServiceNotAllowed");
}

String forwardTo=service.getForwardTo();
if(forwardTo!=null)
{
Class classOfFirstParameter=null;
while(forwardTo!=null)
{
service=(Service)model.get(forwardTo);
if(service==null) break;
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
method=service.getService();
if(service.getSizeOfParameters()>1) break;
if(service.getSizeOfParameters()==1)
{
classOfFirstParameter=correctParameterType(service.getFirstParameter());
if(!(classOfFirstParameter.isInstance(returnedData))) break;
}
initializeScope(request,response,service,obj);
initializeRequestParameters(request,response,service,obj);
initializeAutoWiredProperties(request,response,service,obj);
if(returnedData!=null)
{
try
{
returnedData=method.invoke(obj,classOfFirstParameter.cast(returnedData));
}
catch(InvocationTargetException invocationTargetException)
{
throw new ServiceException("ServiceInvocation");
}
catch(IllegalAccessException illegalAccessException)
{
throw new ServiceException("IllegalServiceAccess");
}
catch(IllegalArgumentException illegalArgumentException)
{
throw new ServiceException("InvalidMethodParameters");
}
}//if ends here
else
{
try
{
returnedData=method.invoke(obj);
}catch(InvocationTargetException invocationTargetException)
{
throw new ServiceException("ServiceInvocation");
}
catch(IllegalAccessException illegalAccessException)
{
throw new ServiceException("IllegalServiceAccess");
}
catch(IllegalArgumentException illegalArgumentException)
{
throw new ServiceException("InvalidMethodParameters");
}
}//else ends here
forwardTo=service.getForwardTo();
}//while loop ends
if(isClientSideTechnology(forwardTo))
{
RequestDispatcher requestDispatcher=request.getRequestDispatcher(forwardTo);
requestDispatcher.forward(request,response);
return;
}
}//if condition ends here
sendBackReturnedData(returnedData,gson,pw);
}catch(ServiceException serviceException)
{
System.out.println("ServiceException:"+serviceException.getMessage());
}catch(IOException ioException)
{
System.out.println(ioException);
}catch(ServletException servletException)
{

}
}

public void doGet(HttpServletRequest request,HttpServletResponse response)
{
doProcessing(request,response,"GET");
}

public void doPost(HttpServletRequest request,HttpServletResponse response)
{
doProcessing(request,response,"POST");
}

}