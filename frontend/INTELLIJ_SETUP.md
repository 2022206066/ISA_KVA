# IntelliJ Setup for Cinema Ticket System

This guide explains how to set up and run the Cinema Ticket System project in IntelliJ IDEA.

## Included Run Configurations

We've included IntelliJ run configurations to make it easier to run the frontend:

1. **Angular Frontend with Setup** - This configuration runs the complete setup process including dependency installation and starting the development server

This configuration will appear in the run configuration dropdown in IntelliJ IDEA if you open the `frontend` directory as a project.

## Running Angular Frontend Automatically on Project Start

### Option 1: Use the Run Configuration

1. In IntelliJ, select the "Angular Frontend with Setup" run configuration from the dropdown
2. Click the run button (green triangle)

This will:
1. Install all dependencies
2. Run pre-flight checks
3. Start the development server

### Option 2: Set Up a Startup Task

To make IntelliJ execute the setup script automatically when a project opens:

1. Go to **File → Settings → Tools → Startup Tasks**
2. Click the **+** button to add a new task
3. Select "Run Configuration" and choose "Angular Frontend with Setup"
4. Click **OK** to save

Now whenever you open the project, IntelliJ will automatically run the setup script.

### Option 3: Create a Run Configuration for the Whole Application

You can create a compound run configuration that starts both the backend and frontend:

1. Go to **Run → Edit Configurations**
2. Click the **+** button and select "Compound"
3. Name it "Full Application"
4. Add your Spring Boot run configuration
   - Make sure the working directory is set to the backend folder
5. Add the "Angular Frontend with Setup" configuration
6. Click **OK** to save

Now you can start both the backend and frontend with a single click.

## Manual Approach

If you prefer to run the scripts manually:

1. Navigate to the `frontend` directory in a terminal
2. Run the setup script:
   ```
   setup.bat
   ```

The script will automatically install dependencies and start the development server.

## Troubleshooting

If you encounter issues with the run configurations:

1. Make sure you have Node.js installed
2. Ensure IntelliJ recognizes your Node.js installation:
   - Go to **File → Settings → Languages & Frameworks → Node.js**
   - Check that the Node.js interpreter is properly configured
3. Try running the commands manually in a terminal to see if there are any specific errors 