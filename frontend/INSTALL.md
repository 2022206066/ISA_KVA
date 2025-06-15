# Cinema Ticket System Frontend Installation

This document provides instructions for setting up the Angular frontend for the Cinema Ticket System.

## Prerequisites

- Node.js (v16 or later)
- npm (v7 or later)

## Installation and Startup

The frontend setup process has been streamlined into a single script that handles both installation and starting the development server.

### Option 1: Using the Setup Script (Recommended)

1. Navigate to the frontend directory:
   ```
   cd ISA_KVA/frontend
   ```

2. Run the setup script:
   ```
   setup.bat
   ```

   This script will:
   - Automatically install all required dependencies
   - Run pre-flight checks to ensure your environment is correctly set up
   - Start the Angular development server

   The application will be available at http://localhost:5500

### Option 2: Using IntelliJ IDEA

If you're using IntelliJ IDEA, we've included a run configuration to make setup easier:

1. Open the project in IntelliJ IDEA
2. Use the "Angular Frontend with Setup" run configuration to install dependencies and start the server

For more detailed IntelliJ setup instructions, see [INTELLIJ_SETUP.md](./INTELLIJ_SETUP.md).

### Option 3: Manual Installation

If you prefer to run the commands manually:

1. Navigate to the frontend directory:
   ```
   cd ISA_KVA/frontend
   ```

2. Install the npm dependencies:
   ```
   npm install
   ```

3. Start the development server:
   ```
   npm start
   ```

## Fixing Linter Errors

The linter errors shown in the IDE are related to missing TypeScript declarations for Angular and other libraries. These errors will be automatically resolved once you complete the npm install step, as the required type definitions will be installed.

## Structure

The frontend follows a standard Angular application structure:

- **src/app/models**: TypeScript interfaces for data models
- **src/app/services**: Angular services for API communication
- **src/app/components**: Angular components organized by feature
- **src/app/guards**: Route guards for authentication and authorization

## Backend Communication

The frontend is configured to communicate with the backend API running at http://localhost:8080/api. If your backend is running on a different URL, update the API URL in the environment files:

- src/environments/environment.ts
- src/environments/environment.prod.ts 