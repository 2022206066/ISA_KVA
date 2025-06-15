@echo off
echo Starting Application directly using Java...

cd backend
set JAVA_HOME=C:\Users\Windrider\.jdks\ms-21.0.7

:: Build the project
echo Building the application...
%JAVA_HOME%\bin\java -version
call .\gradlew assemble

:: Run the application using Java directly
echo Running the application...
%JAVA_HOME%\bin\java -cp build/libs/demo-0.0.1-SNAPSHOT.jar;build/classes/java/main org.aleksa.Application

pause 