# procedure-scheduling
This is a demo application.

# Require:
1. Java 10
2. Tomcat 8.5
3. Maven

# How to:
1. Go to root directory for the project and execute "mvn clean install"
2. Go to target directory for "procedure-scheduling-web" and copy war content to %CATALINA_HOME%\webapps\ROOT directory for tomcat (or just copy war file to %CATALINA_HOME%\webapps, but it affected url).
3. Go to server.xml for tomcat and add redirect section from default 8080 to 9999. Also add new connector for 9999.
4. Execute startup.bat (or startup.sh) from %CATALINA_HOME%\bin where %CATALINA_HOME% is the tomcat home directory.
5. Open a browser and go to http://localhost:9999.

(also you can just checkout the project and start main class - com.procedure.scheduling.ProcedureSchedulingApplication as regular java application).

# What is done:
1. Custom scheduler which based on Jquery and Bootstrap.
2. Back-and works with in-memory database (H2). I think H2 is enough for the demo.
3. Statuses for events are changed automatically (based on Quartz) depending on current time.
4. If one user change some info for event (or add new event), the changes will flushed to all users (based on websockets).
5. GUI has been created with help of thymeleaf, but thymeleaf uses not on the 100%.

# What is NOT done:
1. Unit testing. Not implemented here because it just a testing\demo application and it takes the time. Also application looks simple.
2. Client-side validation on adding\editing events (date overlapping, for example)
3. Searching for available patients and doctors for the current time. Currently "availability" is not depending on the date period (if system has event for 10:30 for Doctor 2, user cannot able to add him for event for 20:30 even if event for 10:30 has beeh finished at 11:00)
4. Adding of new rooms and doctors.
5. Real RDBMS is not implemented. But adding of postgre/mysql/etc with help of liquibase/flyway/etc will take not more that 10 minutes.
