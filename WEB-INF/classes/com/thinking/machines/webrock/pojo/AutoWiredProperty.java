package com.thinking.machines.webrock.pojo;
import java.lang.reflect.*;
public class AutoWiredProperty
{
private String nameKey;
private Field field;
private Method method;
private Class parameterTypes[];
public AutoWiredProperty()
{
this.field=null;
this.method=null;
this.parameterTypes=null;
}
public void setField(Field field)
{
this.field=field;
}
public Field getField()
{
return this.field;
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
public int getSizeOfParameters()
{
return this.parameterTypes.length;
}
public Class[] getParameters()
{
return this.parameterTypes;
}
public void setNameKey(String name)
{
this.nameKey=name;
}
public String getNameKey()
{
return this.nameKey;
}
public Class getFirstParameter()
{
if(this.parameterTypes.length!=0) return this.parameterTypes[0];
return null;
}
}