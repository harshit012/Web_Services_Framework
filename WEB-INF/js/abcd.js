class StudentService
{
add(arg0)
{
var jsonString=JSON.stringify(arg0);
var prm=new Promise(function(resolve,reject)
{
var xmlHttpRequest=new XMLHttpRequest();
xmlHttpRequest.onreadystatechange=function(){
if(this.readyState==4)
{
if(this.status==200)
{
var responseData=this.responseText;
var dataObj=JSON.parse(responseData);
resolve(dataObj);
}
else
{
reject(this.responseText);
}
}
};
xmlHttpRequest.open('POST','schoolService/studentService/add',true);
xmlHttpRequest.setRequestHeader("Content-type","application/json");
xmlHttpRequest.send(jsonString);
});
return prm;
}
update(arg0)
{
var jsonString=JSON.stringify(arg0);
var prm=new Promise(function(resolve,reject)
{
var xmlHttpRequest=new XMLHttpRequest();
xmlHttpRequest.onreadystatechange=function(){
if(this.readyState==4)
{
if(this.status==200)
{
var responseData=this.responseText;
var dataObj=JSON.parse(responseData);
resolve(dataObj);
}
else
{
reject(this.responseText);
}
}
};
xmlHttpRequest.open('POST','schoolService/studentService/update',true);
xmlHttpRequest.setRequestHeader("Content-type","application/json");
xmlHttpRequest.send(jsonString);
});
return prm;
}
delete(arg0)
{
var prm=new Promise(function(resolve,reject)
{
var xmlHttpRequest=new XMLHttpRequest();
xmlHttpRequest.onreadystatechange=function(){
if(this.readyState==4)
{
if(this.status==200)
{
var responseData=this.responseText;
var dataObj=JSON.parse(responseData);
resolve(dataObj);
}
else
{
reject(this.responseText);
}
}
};
var queryString="";
queryString=queryString+"rollNumber";
queryString=queryString+"=";
queryString=queryString+encodeURI(arg0);
var requestURL='schoolService/studentService/delete' +'?'+queryString;
xmlHttpRequest.open('GET',requestURL,true);
xmlHttpRequest.send();
});
return prm;
}
getAll()
{
var prm=new Promise(function(resolve,reject)
{
var xmlHttpRequest=new XMLHttpRequest();
xmlHttpRequest.onreadystatechange=function(){
if(this.readyState==4)
{
if(this.status==200)
{
var responseData=this.responseText;
var dataObj=JSON.parse(responseData);
resolve(dataObj);
}
else
{
reject(this.responseText);
}
}
};
var queryString="";
var requestURL='schoolService/studentService/getAll' +'?'+queryString;
xmlHttpRequest.open('GET',requestURL,true);
xmlHttpRequest.send();
});
return prm;
}
getByRollNumber(arg0)
{
var prm=new Promise(function(resolve,reject)
{
var xmlHttpRequest=new XMLHttpRequest();
xmlHttpRequest.onreadystatechange=function(){
if(this.readyState==4)
{
if(this.status==200)
{
var responseData=this.responseText;
var dataObj=JSON.parse(responseData);
resolve(dataObj);
}
else
{
reject(this.responseText);
}
}
};
var queryString="";
queryString=queryString+"rollNumber";
queryString=queryString+"=";
queryString=queryString+encodeURI(arg0);
var requestURL='schoolService/studentService/getByRollNumber' +'?'+queryString;
xmlHttpRequest.open('GET',requestURL,true);
xmlHttpRequest.send();
});
return prm;
}
}

class login
{
validate()
{
var prm=new Promise(function(resolve,reject)
{
var xmlHttpRequest=new XMLHttpRequest();
xmlHttpRequest.onreadystatechange=function(){
if(this.readyState==4)
{
if(this.status==200)
{
var responseData=this.responseText;
var dataObj=JSON.parse(responseData);
resolve(dataObj);
}
else
{
reject(this.responseText);
}
}
};
var queryString="";
var requestURL='schoolService/login/validate' +'?'+queryString;
xmlHttpRequest.open('GET',requestURL,true);
xmlHttpRequest.send();
});
return prm;
}
}

class EFGH
{
tom()
{
var prm=new Promise(function(resolve,reject)
{
var xmlHttpRequest=new XMLHttpRequest();
xmlHttpRequest.onreadystatechange=function(){
if(this.readyState==4)
{
if(this.status==200)
{
var responseData=this.responseText;
var dataObj=JSON.parse(responseData);
resolve(dataObj);
}
else
{
reject(this.responseText);
}
}
};
var queryString="";
var requestURL='schoolService/efgh/tom' +'?'+queryString;
xmlHttpRequest.open('GET',requestURL,true);
xmlHttpRequest.send();
});
return prm;
}
justDoIt()
{
var prm=new Promise(function(resolve,reject)
{
var xmlHttpRequest=new XMLHttpRequest();
xmlHttpRequest.onreadystatechange=function(){
if(this.readyState==4)
{
if(this.status==200)
{
var responseData=this.responseText;
var dataObj=JSON.parse(responseData);
resolve(dataObj);
}
else
{
reject(this.responseText);
}
}
};
var queryString="";
var requestURL='schoolService/efgh/justDoIt' +'?'+queryString;
xmlHttpRequest.open('GET',requestURL,true);
xmlHttpRequest.send();
});
return prm;
}
sam()
{
var prm=new Promise(function(resolve,reject)
{
var xmlHttpRequest=new XMLHttpRequest();
xmlHttpRequest.onreadystatechange=function(){
if(this.readyState==4)
{
if(this.status==200)
{
var responseData=this.responseText;
var dataObj=JSON.parse(responseData);
resolve(dataObj);
}
else
{
reject(this.responseText);
}
}
};
var queryString="";
var requestURL='schoolService/efgh/sam' +'?'+queryString;
xmlHttpRequest.open('GET',requestURL,true);
xmlHttpRequest.send();
});
return prm;
}
}

class Student
{
constructor()
{
this.rollNumber=0;
this.name="";
this.gender='\0';
}
}

