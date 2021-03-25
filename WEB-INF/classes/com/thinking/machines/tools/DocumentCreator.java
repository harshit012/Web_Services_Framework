package com.thinking.machines.tools;
import java.lang.reflect.*;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;  
import com.itextpdf.kernel.font.PdfFontFactory; 
import com.itextpdf.kernel.font.PdfFont;
import java.util.*;
import java.io.*;
import com.thinking.machines.webrock.annotations.*;

public class DocumentCreator
{      
public static Table createExceptionTable(java.util.List<Cell> exceptionNameCells,java.util.List<Cell> exceptionDescriptionCells)
{
Cell c;
float [] pointColumnWidths={25F,170F, 350F};   
Table table=new Table(pointColumnWidths);  
try
{
PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
c=new Cell().add(new Paragraph("S.No"));
c.setFont(font);
c.setTextAlignment(TextAlignment.CENTER);
table.addCell(c);
c=new Cell().add(new Paragraph("Exception"));
c.setFont(font);
c.setTextAlignment(TextAlignment.CENTER);
exceptionNameCells.add(c);
table.addCell(c);
c=new Cell().add(new Paragraph("Description"));
c.setFont(font);
c.setTextAlignment(TextAlignment.CENTER);
exceptionDescriptionCells.add(c);
table.addCell(c);

c=new Cell().add(new Paragraph("1"));
c.setTextAlignment(TextAlignment.RIGHT);
table.addCell(c);
c=new Cell().add(new Paragraph("SetterNotFound"));
exceptionNameCells.add(c);
table.addCell(c);
c=new Cell().add(new Paragraph("This Exception is raised when the definition of setter method is not found."));
exceptionDescriptionCells.add(c);
table.addCell(c);

c=new Cell().add(new Paragraph("2"));
c.setTextAlignment(TextAlignment.RIGHT);
table.addCell(c);
c=new Cell().add(new Paragraph("GaurdNotFound"));
exceptionNameCells.add(c);
table.addCell(c);
c=new Cell().add(new Paragraph("When SecuredAccess Annotation is applied on any class and the method, act as a guard is not found."));
exceptionDescriptionCells.add(c);
table.addCell(c);

c=new Cell().add(new Paragraph("3"));
c.setTextAlignment(TextAlignment.RIGHT);
table.addCell(c);
c=new Cell().add(new Paragraph("PostNotFound"));
exceptionNameCells.add(c);
table.addCell(c);
c=new Cell().add(new Paragraph("When SecuredAccess Annotation is applied on any class and the Class, act as a post is not found."));
exceptionDescriptionCells.add(c);
table.addCell(c);

c=new Cell().add(new Paragraph("4"));
c.setTextAlignment(TextAlignment.RIGHT);
table.addCell(c);
c=new Cell().add(new Paragraph("ServiceNotFound"));
exceptionNameCells.add(c);
table.addCell(c);
c=new Cell().add(new Paragraph("It is thrown when accessing a service which is not found."));
exceptionDescriptionCells.add(c);
table.addCell(c);

c=new Cell().add(new Paragraph("5"));
c.setTextAlignment(TextAlignment.RIGHT);
table.addCell(c);
c=new Cell().add(new Paragraph("ServiceClassNotFound"));
exceptionNameCells.add(c);
table.addCell(c);
c=new Cell().add(new Paragraph("This exception is raised when accessing a class having Path annotion on it, is not found."));
exceptionDescriptionCells.add(c);
table.addCell(c);

c=new Cell().add(new Paragraph("6"));
c.setTextAlignment(TextAlignment.RIGHT);
table.addCell(c);
c=new Cell().add(new Paragraph("BaseURLPatternNotFound"));
exceptionNameCells.add(c);
table.addCell(c);
c=new Cell().add(new Paragraph("It is thrown when base URL is not provided in web.xml."));
exceptionDescriptionCells.add(c);
table.addCell(c);

c=new Cell().add(new Paragraph("7"));
c.setTextAlignment(TextAlignment.RIGHT);
table.addCell(c);
c=new Cell().add(new Paragraph("InvalidMethodParameters"));
exceptionNameCells.add(c);
table.addCell(c);
c=new Cell().add(new Paragraph("It is thrown when the number of parameters is out of range or the type of the parameters is not according to the specified guidelines."));
exceptionDescriptionCells.add(c);
table.addCell(c);

c=new Cell().add(new Paragraph("8"));
c.setTextAlignment(TextAlignment.RIGHT);
table.addCell(c);
c=new Cell().add(new Paragraph("InvalidServiceParameters"));
exceptionNameCells.add(c);
table.addCell(c);
c=new Cell().add(new Paragraph("This is raised when the service does not follow the specified guidelines to declare parameter variables."));
exceptionDescriptionCells.add(c);
table.addCell(c);

c=new Cell().add(new Paragraph("9"));
c.setTextAlignment(TextAlignment.RIGHT);
table.addCell(c);
c=new Cell().add(new Paragraph("ServiceInvocation"));
exceptionNameCells.add(c);
table.addCell(c);
c=new Cell().add(new Paragraph("This is thrown when the invoked service throws an exception."));
exceptionDescriptionCells.add(c);
table.addCell(c);

c=new Cell().add(new Paragraph("10"));
c.setTextAlignment(TextAlignment.RIGHT);
table.addCell(c);
c=new Cell().add(new Paragraph("IllegalServiceAccess"));
exceptionNameCells.add(c);
table.addCell(c);
c=new Cell().add(new Paragraph("This is thrown when the access of method is not according to the specified guidelines."));
exceptionDescriptionCells.add(c);
table.addCell(c);

c=new Cell().add(new Paragraph("11"));
c.setTextAlignment(TextAlignment.RIGHT);
table.addCell(c);
c=new Cell().add(new Paragraph("ServiceClassInstantiation"));
exceptionNameCells.add(c);
table.addCell(c);
c=new Cell().add(new Paragraph("This is raised when the class is not instantiable."));
exceptionDescriptionCells.add(c);
table.addCell(c);

c=new Cell().add(new Paragraph("12"));
c.setTextAlignment(TextAlignment.RIGHT);
table.addCell(c);
c=new Cell().add(new Paragraph("PathNotApplied"));
exceptionNameCells.add(c);
table.addCell(c);
c=new Cell().add(new Paragraph("This is thrown to indicate that, The Path annotation must be applied on the service."));
exceptionDescriptionCells.add(c);
table.addCell(c);

c=new Cell().add(new Paragraph("13"));
c.setTextAlignment(TextAlignment.RIGHT);
table.addCell(c);
c=new Cell().add(new Paragraph("ServiceNotAllowed"));
exceptionNameCells.add(c);
table.addCell(c);
c=new Cell().add(new Paragraph("This is thrown when the request type do not match."));
exceptionDescriptionCells.add(c);
table.addCell(c);

c=new Cell().add(new Paragraph("14"));
c.setTextAlignment(TextAlignment.RIGHT);
table.addCell(c);
c=new Cell().add(new Paragraph("PackagePrefixNotFound"));
exceptionNameCells.add(c);
table.addCell(c);
c=new Cell().add(new Paragraph("This is raised when the package prefix is not mentioned in web.xml."));
exceptionDescriptionCells.add(c);
table.addCell(c);

c=new Cell().add(new Paragraph("15"));
c.setTextAlignment(TextAlignment.RIGHT);
table.addCell(c);
c=new Cell().add(new Paragraph("AmbigiousRequestType"));
exceptionNameCells.add(c);
table.addCell(c);
c=new Cell().add(new Paragraph("This is thrown when the GET & POST Annotation applied on same service."));
exceptionDescriptionCells.add(c);
table.addCell(c);


}catch(IOException ioException)
{
System.out.println(ioException);
}
return table;
}

public static void addServiceClassInDoc(Document doc,java.util.HashMap<String,java.util.List<Method>> serviceClassMap)
{
Cell c;
float [] pointColumnWidths={170F,375F};  
Table table=null;
int cnt=0;
int count=0;
Class cls=null;
Method methods[]=null;
Parameter parameters[]=null;
Field[] fields=null;
java.lang.annotation.Annotation[] annotations=null;
String annotationsDetail="";
try
{
Paragraph heading=new Paragraph("Service Classes \n\n").setFont(PdfFontFactory.createFont(FontConstants.TIMES_BOLD));
doc.add(heading);
PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
String packageName="";
for(Map.Entry<String,java.util.List<Method>> entry:serviceClassMap.entrySet())
{
packageName=entry.getKey();
cls=Class.forName(packageName);
table=new Table(pointColumnWidths);  

c=new Cell().add(new Paragraph("Service Class"));
c.setFont(font);
table.addCell(c);
c=new Cell().add(new Paragraph(cls.getSimpleName()));
table.addCell(c);


c=new Cell().add(new Paragraph("Package"));
c.setFont(font);
table.addCell(c);
c=new Cell().add(new Paragraph(packageName));
table.addCell(c);

annotations=cls.getAnnotations();
annotationsDetail="";
count=0;
for(java.lang.annotation.Annotation annotation:annotations)
{
if(count!=0) annotationsDetail=annotationsDetail+",";
annotationsDetail=annotationsDetail+annotation.annotationType().getSimpleName();
count++;
}

c=new Cell().add(new Paragraph("Annotations"));
c.setFont(font);
table.addCell(c);
c=new Cell();
if(annotationsDetail.length()!=0) c.add(new Paragraph(annotationsDetail));
else c.add(new Paragraph("None"));
table.addCell(c);

doc.add(table);
doc.add(new Paragraph("\n"));

fields=cls.getDeclaredFields();
for(Field field:fields)
{
table=new Table(pointColumnWidths);  
c=new Cell().add(new Paragraph("Service Class"));
c.setFont(font);
table.addCell(c);
c=new Cell().add(new Paragraph(cls.getSimpleName()));
table.addCell(c);

c=new Cell().add(new Paragraph("Field Name"));
c.setFont(font);
table.addCell(c);
c=new Cell().add(new Paragraph(field.getName()));
table.addCell(c);

c=new Cell().add(new Paragraph("Field type"));
c.setFont(font);
table.addCell(c);
c=new Cell().add(new Paragraph(field.getType().getName()));
table.addCell(c);


annotations=field.getDeclaredAnnotations();
annotationsDetail="";
count=0;
for(java.lang.annotation.Annotation annotation:annotations)
{
if(count!=0) annotationsDetail=annotationsDetail+",";
annotationsDetail=annotationsDetail+annotation.annotationType().getSimpleName();
count++;
}
c=new Cell().add(new Paragraph("Annotations"));
c.setFont(font);
table.addCell(c);
c=new Cell();
if(annotationsDetail.length()!=0) c.add(new Paragraph(annotationsDetail));
else c.add(new Paragraph("None"));
table.addCell(c);

doc.add(table);
doc.add(new Paragraph("\n"));
}

methods=cls.getDeclaredMethods();
for(Method method:methods)
{
table=new Table(pointColumnWidths);  
table=new Table(pointColumnWidths);  

c=new Cell().add(new Paragraph("Service Class"));
c.setFont(font);
table.addCell(c);
c=new Cell().add(new Paragraph(cls.getSimpleName()));
table.addCell(c);

c=new Cell().add(new Paragraph("Service"));
c.setFont(font);
table.addCell(c);
c=new Cell().add(new Paragraph(method.getName()));
table.addCell(c);
 
c=new Cell().add(new Paragraph("Return Type"));
c.setFont(font);
table.addCell(c);
c=new Cell().add(new Paragraph(method.getReturnType().getName()));
table.addCell(c);


annotations=method.getDeclaredAnnotations();
annotationsDetail="";
count=0;
for(java.lang.annotation.Annotation annotation:annotations)
{
if(count!=0) annotationsDetail=annotationsDetail+",";
annotationsDetail=annotationsDetail+annotation.annotationType().getSimpleName();
count++;
}
c=new Cell().add(new Paragraph("Annotations"));
c.setFont(font);
table.addCell(c);
c=new Cell();
if(annotationsDetail.length()!=0) c.add(new Paragraph(annotationsDetail));
else c.add(new Paragraph("None"));
table.addCell(c);

parameters=method.getParameters();
c=new Cell().add(new Paragraph("Number of Parameters"));
c.setFont(font);
table.addCell(c);
c=new Cell().add(new Paragraph(String.valueOf(parameters.length)));
table.addCell(c);


cnt=0;
for(Parameter parameter:parameters)
{
c=new Cell().add(new Paragraph("Parameter "+String.valueOf(cnt+1)+" type"));
c.setFont(font);
table.addCell(c);
c=new Cell().add(new Paragraph(parameter.getType().getName()));
table.addCell(c);

annotations=parameter.getDeclaredAnnotations();
annotationsDetail="";
count=0;
for(java.lang.annotation.Annotation annotation:annotations)
{
if(count!=0) annotationsDetail=annotationsDetail+",";
annotationsDetail=annotationsDetail+annotation.annotationType().getSimpleName();
count++;
}
c=new Cell().add(new Paragraph("Annotations"));
c.setFont(font);
table.addCell(c);
c=new Cell();
if(annotationsDetail.length()!=0) c.add(new Paragraph(annotationsDetail));
else c.add(new Paragraph("None"));
table.addCell(c);
cnt++;
}
doc.add(table);
doc.add(new Paragraph("\n"));
}//method loop ends here
doc.add(new AreaBreak());     
}//main loop ends here
}catch(Exception exception)
{
System.out.println(exception);
}
}

public static void doProcessing(String file,int beginFrom,java.util.HashMap<String,java.util.List<Method>> serviceClassMap) 
{
int len,value;
Class cls=null;
Method methods[]=null;
Parameter parameters[]=null;
java.util.List<Method> listOfMethods=null;
Field fields[]=null;
boolean flag=false;
String packageName=file.replace("\\",".");
if(packageName.charAt(beginFrom-1)!='.')
{
if(packageName.charAt(beginFrom-2)=='.') beginFrom-=1;
if(packageName.charAt(beginFrom)=='.') beginFrom+=1;
}
len=packageName.length();
packageName=packageName.substring(beginFrom,len-6);
try
{
cls=Class.forName(packageName);
if(cls.isAnnotationPresent(Path.class))
{
methods=cls.getDeclaredMethods();
for(Method method:methods)
{
if(method.isAnnotationPresent(Path.class))
{
if(serviceClassMap.containsKey(packageName))
{
listOfMethods=serviceClassMap.get(packageName);
listOfMethods.add(method);
}else
{
listOfMethods=new LinkedList<>();
listOfMethods.add(method);
serviceClassMap.put(packageName,listOfMethods);
}
flag=true;
}
}
}
}catch(Exception exception)
{
System.out.println(exception);
}
}





public static void startProcessing(File[] arr,int index,int length,java.util.HashMap<String,java.util.List<Method>> serviceClassMap) 
{
String absPath="";
if(index == arr.length) 
return; 
if(arr[index].isFile()) 
{
absPath=arr[index].getAbsolutePath();
if(absPath.endsWith(".class")) doProcessing(absPath,length,serviceClassMap);
}
else if(arr[index].isDirectory()) 
{ 
startProcessing(arr[index].listFiles(), 0,length,serviceClassMap); 
} 
startProcessing(arr,++index,length,serviceClassMap); 
} 


public static void main(String args[]) throws Exception 
{     
try
{
int len;
String path=args[1];
len=path.length();
java.util.List<Cell> exceptionNameCells=new LinkedList<>();
java.util.List<Cell> exceptionDescriptionCells=new LinkedList<>();
java.util.HashMap<String,java.util.List<Method>> serviceClassMap=new java.util.HashMap<>();

String dest=args[0];
PdfWriter writer=new PdfWriter(dest);       
PdfDocument pdf = new PdfDocument(writer);                  
Document doc = new Document(pdf);                       
Table exceptionTable=createExceptionTable(exceptionNameCells,exceptionDescriptionCells);
Paragraph heading=new Paragraph("ServiceException \n\n").setFont(PdfFontFactory.createFont(FontConstants.TIMES_BOLD));
doc.add(heading);
doc.add(exceptionTable);         
doc.add(new AreaBreak());       
File file=new File(path);
if(!file.exists())
{
System.out.println("Invalid path:");
}
else
{
//file exists
if(file.isDirectory()) startProcessing(file.listFiles(),0,len,serviceClassMap);
addServiceClassInDoc(doc,serviceClassMap);
}
doc.close();
System.out.println("Document generated successfully");   
}catch(IOException ioException)
{
System.out.println(ioException);
}
}     
}