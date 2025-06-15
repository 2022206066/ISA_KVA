package org.aleksa.controllers;

import org.aleksa.dtos.AuthDTO;
import org.aleksa.dtos.UserDTO;
import org.aleksa.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("user")
@CrossOrigin(origins = {"http://localhost:5500"}, allowedHeaders = "*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<UserDTO> newUser(@RequestBody AuthDTO value) {
        return ResponseEntity.ok(userService.createUser(value));
    }

    @GetMapping("all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("login")
    public ResponseEntity<UserDTO> login(@RequestBody AuthDTO value) {
        UserDTO userDto = userService.refreshUser(value);
        if(userDto == null) {return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();}
        return ResponseEntity.ok(userDto);
    }
    
    @PutMapping("profile")
    public ResponseEntity<UserDTO> updateProfile(@RequestBody UserDTO userDto) {
        UserDTO updatedUser = userService.updateUser(userDto);
        if(updatedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedUser);
    }
}
