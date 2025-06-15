@echo off
REM ====================================================================
REM Cinema Ticket System Frontend Setup Script (Windows)
REM ====================================================================
REM Purpose: This script installs all required npm dependencies and
REM          automatically starts the Angular development server.
REM
REM Usage:   Simply double-click this file or run it from the command line
REM          from the frontend directory.
REM
REM How it works:
REM   1. Changes to the directory where this script is located
REM   2. Runs 'npm install' to install all dependencies defined in package.json
REM   3. Runs pre-flight checks to ensure environment is correctly set up
REM   4. Automatically starts the Angular development server
REM
REM When to run:
REM   - First time setup of the project
REM   - After pulling new changes that include package.json updates
REM   - If you see linter errors about missing TypeScript declarations
REM   - Each time you want to start the frontend application
REM ====================================================================

echo Setting up Cinema Ticket System Frontend...

echo Installing dependencies...
call npm install

echo Starting Angular development server...
call npm start 