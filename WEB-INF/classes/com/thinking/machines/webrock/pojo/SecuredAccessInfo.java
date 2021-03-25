package com.thinking.machines.webrock.pojo;
import com.thinking.machines.webrock.annotations.*;
import java.lang.reflect.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class SecuredAccessInfo
{
private Class className;
private Method method;
private Class[] parameterTypes;
private boolean hasInjectApplicationScope;
private boolean hasInjectApplicationDirectory;
private boolean hasInjectSessionScope;
private boolean hasInjectRequestScope;

public SecuredAccessInfo()
{
this.className=null;
this.method=null;
this.parameterTypes=null;
this.hasInjectApplicationScope=false;
this.hasInjectApplicationDirectory=false;
this.hasInjectSessionScope=false;
this.hasInjectRequestScope=false;
}



private int hasMethod(String methodName,Method methods[])
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



private void performValidation()
{
Class[] parameterTypes=null;
int e;
Method[] methods=this.className.getMethods();
if(this.className.isAnnotationPresent(InjectApplicationScope.class)) 
{
e=hasMethod("setApplicationScope",methods);
if(e!=-1)
{
parameterTypes=methods[e].getParameterTypes();
if(parameterTypes.length==1)
{
if(parameterTypes[0].getName().equals("com.thinking.machines.webrock.pojo.ApplicationScope")) this.hasInjectApplicationScope=true;
}
}
}
if(this.className.isAnnotationPresent(InjectApplicationDirectory.class)) 
{
e=hasMethod("setApplicationDirectory",methods);
if(e!=-1)
{
parameterTypes=methods[e].getParameterTypes();
if(parameterTypes.length==1)
{
if(parameterTypes[0].getName().equals("com.thinking.machines.webrock.pojo.ApplicationDirectory")) this.hasInjectApplicationDirectory=true;
}
}
}
if(this.className.isAnnotationPresent(InjectRequestScope.class)) 
{
e=hasMethod("setRequestScope",methods);
if(e!=-1)
{
parameterTypes=methods[e].getParameterTypes();
if(parameterTypes.length==1)
{
if(parameterTypes[0].getName().equals("com.thinking.machines.webrock.pojo.RequestScope")) this.hasInjectRequestScope=true;
}
}
}
if(this.className.isAnnotationPresent(InjectSessionScope.class))
{
e=hasMethod("setSessionScope",methods);
if(e!=-1)
{
parameterTypes=methods[e].getParameterTypes();
if(parameterTypes.length==1)
{
if(parameterTypes[0].getName().equals("com.thinking.machines.webrock.pojo.SessionScope")) this.hasInjectSessionScope=true;
}
}
}
}



public void setClassName(Class className)
{
this.className=className;
performValidation();
}
public Class getClassName()
{
return this.className;
}
public void setMethod(Method method)
{
this.method=method;
this.parameterTypes=method.getParameterTypes();
}
public Method getMethod()
{
return this.method;
}
public Class[] getParameterTypes()
{
return this.parameterTypes;
}
public boolean hasInjectApplicationScopeAnnotation()
{
return this.hasInjectApplicationScope;
}
public boolean hasInjectApplicationDirectoryAnnotation()
{
return this.hasInjectApplicationScope;
}
public boolean hasInjectSessionScopeAnnotation()
{
return this.hasInjectSessionScope;
}
public boolean hasInjectRequestScopeAnnotation()
{
return this.hasInjectRequestScope;
}
}