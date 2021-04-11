# Web-Services-Framework
This is the java based Web Services Framework, which provides an easier and faster way to set up, configure, and create the backend/serverside of the web-application. Now the user do not need to worry about servlets or the configuration of web.xml anymore. 

## Why to use this framework?
* No need to write the code for servlet classes.
* The framework generates a javascript file, Which contains the necessary implementation of all the service classes at backend/serverside.
* No need to do url mappings in XML file. (deployment descriptor)
* User don't need to worry about GET/POST request and how to Handle them.
* The user can easily send/receive the data from client-side to server-side without writing the single line of code of JSON parsing.


## How to use this framework?
1 -> Download web_services.jar file.

2 -> Cut/Copy that jar file to tomcat9/webapps/'Project_Name'/WEB-INF/lib/;

3 -> Some following changes has to be performed in web.xml.

   * You need to specify param-value against param-name 'SERVICE_PACKAGE_PREFIX'. In my project it is 'bobby' as shown below.
    User just need to change/write a single word inside web.xml and that was the param-value against param-name 'SERVICE_PACKAGE_PREFIX' i.e. by default there was "bobby",           user have to change it.
    It is the package prefix like if package name is : "bobby.abc.def" then you need to write 'bobby'. 'bobby' is the name of a folder.

      Note : The SERVICE_PACKAGE_PREFIX mention here i.e. 'bobby' should exist inside tomcat9/webapps/"project name"/WEB-INF/classes/.

      The package consist of service classes in it (services classes are classes using the framework to create web service for requests).
      ```
      <init-param>
      <param-name>SERVICE_PACKAGE_PREFIX</param-name>
      <param-value>bobby</param-value>
      </init-param>
      ```

   * You have to change a single word inside web.xml, instead of 'schoolService' user have to write his/her application entity name there.
      ```
      <servlet>
      <servlet-name>TMWebRock</servlet-name>
      <servlet-class>com.thinking.machines.webrock.TMWebRock</servlet-class>
      </servlet>
      <servlet-mapping>
      <servlet-name>TMWebRock</servlet-name>
      <url-pattern>/schoolService/*</url-pattern>
      </servlet-mapping>
      ```
Now the web.xml has been configured.

4 -> Now copy all the necessary .jar files to tomcat9/webapps/"Project Name"/WEB-INF/lib/.

5 -> Now copy all files inside Dependencies folder and paste them inside tomcat9/lib/. these are all the files you will ever need to create a web service. Our framework is Dependent on some of the these files. some of the jar file may already be present there you can skip those files.

6 -> You are done setting up the environment,now you can use the frameWork easily.
