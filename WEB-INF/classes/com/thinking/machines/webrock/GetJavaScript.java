package com.thinking.machines.webrock;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class GetJavaScript  extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
try
{
PrintWriter pw=response.getWriter();
String path=getServletContext().getRealPath("")+"WEB-INF\\js\\";
String jsFileName=path+request.getParameter("name");
File file=new File(jsFileName);
if(!file.exists())
{
System.out.println("ServiceException: File not found:"+jsFileName);
pw.println("");
return;
}
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
pw.println(randomAccessFile.readLine());
}
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
}

public void doPost(HttpServletRequest request,HttpServletResponse response)
{
doGet(request,response);
}
}