@echo off
echo Starting Cinema Ticket System Backend...
cd %~dp0
set JAVA_HOME=C:\Users\Windrider\.jdks\ms-21.0.7
echo Using Java from: %JAVA_HOME%

echo Building with Gradle...
call gradlew -Dorg.gradle.java.home="%JAVA_HOME%" --no-daemon clean bootRun

echo Done!
pause 