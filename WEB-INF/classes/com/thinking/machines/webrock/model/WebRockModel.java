package com.thinking.machines.webrock.model;
import com.thinking.machines.webrock.pojo.*;
import java.util.*;
public class WebRockModel
{
Map<String,Service> map;
public WebRockModel()
{
this.map=new HashMap<>();
}
public void add(String path,Service service)
{
map.put(path,service);
}
public Service get(String path)
{
return map.get(path);
}
}