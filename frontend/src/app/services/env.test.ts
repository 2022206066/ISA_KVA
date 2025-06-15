import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';

/**
 * This is a test component to verify environment configuration is working properly.
 * It's not meant to be used in production, but is helpful for debugging API connection issues.
 */
@Component({
  selector: 'app-env-test',
  template: `
    <div class="env-test">
      <h2>Environment Test</h2>
      <pre>{{ envInfo }}</pre>
      <button (click)="testApiConnection()">Test API Connection</button>
      <div *ngIf="apiResult">
        <h3>API Response:</h3>
        <pre>{{ apiResult }}</pre>
      </div>
    </div>
  `,
})
export class EnvTestComponent implements OnInit {
  envInfo = '';
  apiResult = '';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.envInfo = `
* Environment loaded: ${JSON.stringify(environment)}
* API URL: ${environment.apiUrl}
* Production mode: ${environment.production ? 'Yes' : 'No'}
    `;
  }

  testApiConnection(): void {
    this.http.get(`${environment.apiUrl}/health`).subscribe(
      (data) => {
        this.apiResult = JSON.stringify(data, null, 2);
      },
      (error) => {
        this.apiResult = `Error connecting to API: ${JSON.stringify(error, null, 2)}`;
      }
    );
  }
}

/**
 * This is a simple file to verify that the environment configuration is working correctly.
 * It's not a proper test file, but it can be used to check if the environment is loaded correctly.
 */

// Check that the environment is defined
console.log('Environment loaded:', environment);

// Check that the API URL is set
console.log('API URL:', environment.apiUrl);

// Expected format of the API URL
const expectedFormat = /^https?:\/\/.+\/api$/;
console.log('API URL format is valid:', expectedFormat.test(environment.apiUrl));

/**
 * How to use this file:
 * 1. After installing dependencies, run: 
 *    npx ts-node src/app/services/env.test.ts
 * 
 * 2. Check the output to verify that the environment is loaded correctly.
 * 
 * Expected output:
 * Environment loaded: { production: false, apiUrl: 'http://localhost:9000/api' }
 * API URL: http://localhost:9000/api
 * API URL format is valid: true
 */ 