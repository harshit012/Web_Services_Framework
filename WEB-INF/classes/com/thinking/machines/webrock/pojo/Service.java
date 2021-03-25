package com.thinking.machines.webrock.pojo;
import java.lang.reflect.*;
import java.util.*;
public class Service
{
private Class serviceClass;
private String path;
private Method service;
private String type;
private String forwardTo;
private int priority;
private boolean injectSessionScope;
private boolean injectApplicationScope;
private boolean injectApplicationDirectory;
private boolean injectRequestScope;
private List<AutoWiredProperty> autoWiredProperties;
private List<RequestParameterInfo> requestParameters;
private Class parameterTypes[];
private SecuredAccessInfo securedAccess;

public Service()
{
this.serviceClass=null;
path="";
service=null;
this.type="";
this.forwardTo=null;
this.priority=-1;
this.injectSessionScope=false;
this.injectApplicationScope=false;
this.injectApplicationDirectory=false;
this.injectRequestScope=false;
this.autoWiredProperties=null;
this.requestParameters=null;
this.parameterTypes=null;
this.securedAccess=null;
}
public void setServiceClass(Class serviceClass)
{
this.serviceClass=serviceClass;
}
public Class getServiceClass()
{
return this.serviceClass;
}
public void setPath(String path)
{
this.path=path;
}
public String getPath()
{
return this.path;
}
public void setService(Method service)
{
this.service=service;
this.parameterTypes=service.getParameterTypes();
}
public Method getService()
{
return this.service;
}
public void setType(String type)
{
this.type=type;
}
public String getType()
{
return this.type;
}
public void setForwardTo(String forwardTo)
{
this.forwardTo=forwardTo;
}
public String getForwardTo()
{
return this.forwardTo;
}
public void setPriority(int priority)
{
this.priority=priority;
}
public int getPriority()
{
return this.priority;
}
public void injectSessionScope(boolean value)
{
this.injectSessionScope=value;
}
public void injectApplicationScope(boolean value)
{
this.injectApplicationScope=value;
}
public void injectApplicationDirectory(boolean value)
{
this.injectApplicationDirectory=value;
}
public void injectRequestScope(boolean value)
{
this.injectRequestScope=value;
}
public boolean injectSessionScope()
{
return this.injectSessionScope;
}
public boolean injectApplicationScope()
{
return this.injectApplicationScope;
}
public boolean injectApplicationDirectory()
{
return this.injectApplicationDirectory;
}
public boolean injectRequestScope()
{
return this.injectRequestScope;
}
public void setAutoWiredProperties(List<AutoWiredProperty> list)
{
this.autoWiredProperties=list;
}
public void setRequestParameters(List<RequestParameterInfo> list)
{
this.requestParameters=list;
}
public List<AutoWiredProperty> getAutoWiredProperties()
{
return this.autoWiredProperties;
}
public List<RequestParameterInfo> getRequestParameters()
{
return this.requestParameters;
}
public int getSizeOfParameters()
{
return this.parameterTypes.length;
}
public Class[] getParameterTypes()
{
return this.parameterTypes;
}
public Class getFirstParameter()
{
if(this.parameterTypes.length!=0) return this.parameterTypes[0];
return null;
}
public void setSecuredAccess(SecuredAccessInfo securedAccess)
{
this.securedAccess=securedAccess;
}
public SecuredAccessInfo getSecuredAccess()
{
return this.securedAccess;
}
}