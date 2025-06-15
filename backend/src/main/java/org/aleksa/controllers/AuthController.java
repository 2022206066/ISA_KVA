package org.aleksa.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.aleksa.dtos.AuthDTO;
import org.aleksa.dtos.UserDTO;
import org.aleksa.security.util.HeaderUtil;
import org.aleksa.security.util.JwtUtil;
import org.aleksa.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = {"http://localhost:5500"}, allowedHeaders = "*")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<UserDTO> register(@RequestBody AuthDTO authRequest) {
        try {
            UserDTO createdUser = userService.createUser(authRequest);
            return ResponseEntity.ok(createdUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .build();
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthDTO authRequest) {
        try {
            UserDTO user = userService.refreshUser(authRequest);
            
            if (user != null) {
                // Generate refresh token and add it to the response
                String refreshToken = JwtUtil.createRefreshToken(user.getUsername());
                user.setRefreshToken(refreshToken);
                return ResponseEntity.ok(user);
            } else {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Invalid credentials");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            }
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Login failed");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    @PostMapping("refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String authHeader) {
        try {
            // Extract the refresh token from the Authorization header
            String refreshToken = HeaderUtil.extractToken(authHeader);
            if (refreshToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            // Decode the refresh token to get the username
            DecodedJWT decodedJWT = JwtUtil.decodeJWT(refreshToken);
            if (decodedJWT == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            String username = decodedJWT.getSubject();
            UserDTO user = userService.findByUsername(username);
            
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            // Create claims for the new token
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            claims.put("role", user.getRole());
            
            // Generate a new access token
            String newAccessToken = JwtUtil.refreshToken(refreshToken, claims);
            
            // Create response with new tokens
            Map<String, String> response = new HashMap<>();
            response.put("accessToken", newAccessToken);
            
            return ResponseEntity.ok(response);
        } catch (JWTVerificationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
} 