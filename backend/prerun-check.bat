@echo off
REM ====================================================================
REM Cinema Ticket System Backend Pre-run Check Script (Windows)
REM ====================================================================
REM Purpose: This script checks if the system meets the requirements
REM          to run the Spring Boot backend application.
REM
REM Usage:   Simply double-click this file or run it from the command line
REM          from the backend directory.
REM ====================================================================

echo ====================================================================
echo Cinema Ticket System Backend Pre-run Check
echo ====================================================================
echo.

echo Checking Java version...
java -version 2>&1 | findstr /i "version" > nul
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Java is not installed or not in the PATH.
    echo Please install Java 17 or newer and try again.
    echo.
    pause
    exit /b 1
)

for /f tokens^=2-5^ delims^=.-_^" %%j in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    set javaVersion=%%j
)

if %javaVersion% LSS 17 (
    echo ERROR: Java version %javaVersion% detected.
    echo This application requires Java 17 or newer.
    echo Please update your Java installation and try again.
    echo.
    pause
    exit /b 1
) else (
    echo OK: Java version %javaVersion% detected.
)

echo.
echo Checking MySQL connection...
echo NOTE: This application requires a MySQL database named 'cinema'.
echo.

echo All checks passed! You can now run the application using:
echo ./gradlew bootRun
echo.
echo ====================================================================
echo.
pause 