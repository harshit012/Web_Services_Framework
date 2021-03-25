package com.thinking.machines.webrock.pojo;
public class ServiceResponse implements java.io.Serializable
{
private Object result;
private Object exception;
private boolean isSuccessful;
public ServiceResponse()
{
this.result=null;
this.exception=null;
this.isSuccessful=false;
}
public void setResult(Object result)
{
this.result=result;
}
public Object getResult()
{
return this.result;
}
public void setException(Object exception)
{
this.exception=exception;
}
public Object getException()
{
return this.exception;
}
public void isSuccessful(boolean success)
{
isSuccessful=success;
}
public boolean isSuccessful()
{
return this.isSuccessful;
}
}