@echo off
echo Starting Cinema Ticket System Backend...

set JAVA_HOME=C:\Users\Windrider\.jdks\ms-21.0.7
echo Using Java: %JAVA_HOME%

cd backend
echo Current directory: %CD%

echo Running with Gradle...
call gradlew -Dorg.gradle.java.home="%JAVA_HOME%" --no-daemon bootRun

pause 