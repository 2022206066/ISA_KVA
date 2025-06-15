@echo off
echo Starting Cinema Ticket System Backend...
cd backend
set JAVA_HOME=C:\Users\Windrider\.jdks\ms-21.0.7
%JAVA_HOME%\bin\java -version
.\gradlew bootRun 