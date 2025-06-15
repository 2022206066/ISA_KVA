/**
 * Cinema Ticket System - Pre-run Check Script
 * 
 * This script verifies that all required files and dependencies are in place
 * before running the application. It's a quick sanity check that helps
 * identify common configuration issues.
 * 
 * Usage:
 *   node prerun-check.js
 * 
 * This script is automatically run by the setup scripts (setup.bat and setup.sh)
 * but can also be run manually.
 */

const fs = require('fs');
const path = require('path');
const { execSync } = require('child_process');

console.log('🔍 Running pre-flight checks for Cinema Ticket System frontend...');

// Check that package.json exists
try {
  const packageJson = require('./package.json');
  console.log('✅ package.json exists');
  
  // Check that it has the required dependencies
  const requiredDeps = [
    '@angular/core',
    '@angular/common',
    '@angular/material',
    '@angular/router',
    'rxjs'
  ];
  
  const missingDeps = requiredDeps.filter(dep => !packageJson.dependencies[dep]);
  if (missingDeps.length > 0) {
    console.error(`❌ Missing dependencies in package.json: ${missingDeps.join(', ')}`);
  } else {
    console.log('✅ All required dependencies are declared in package.json');
  }
} catch (err) {
  console.error('❌ package.json not found or invalid');
}

// Check that environment files exist
const environmentPaths = [
  path.join(__dirname, 'src', 'environments', 'environment.ts'),
  path.join(__dirname, 'src', 'environments', 'environment.prod.ts')
];

for (const envPath of environmentPaths) {
  if (fs.existsSync(envPath)) {
    console.log(`✅ ${path.basename(envPath)} exists`);
    
    // Check that environment file has apiUrl property
    const envContent = fs.readFileSync(envPath, 'utf8');
    if (!envContent.includes('apiUrl')) {
      console.error(`❌ ${path.basename(envPath)} does not contain apiUrl property`);
    }
  } else {
    console.error(`❌ ${path.basename(envPath)} not found`);
  }
}

// Check that node_modules exists (dependencies are installed)
if (fs.existsSync(path.join(__dirname, 'node_modules'))) {
  console.log('✅ node_modules exists (dependencies installed)');
} else {
  console.warn('⚠️ node_modules not found. Run npm install to install dependencies');
}

// Check Angular CLI version if available
try {
  const ngVersion = execSync('npx ng version', { stdio: 'pipe' }).toString();
  console.log('✅ Angular CLI available:');
  const versionLine = ngVersion.split('\n').find(line => line.includes('Angular CLI'));
  if (versionLine) {
    console.log(`   ${versionLine.trim()}`);
  }
} catch (err) {
  console.warn('⚠️ Angular CLI not available or not in PATH');
}

console.log('\n📋 Pre-flight check complete!');
console.log('   If any issues were found, please address them before running the application.');
console.log('   For detailed setup instructions, see INSTALL.md and INTELLIJ_SETUP.md'); 