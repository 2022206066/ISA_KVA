package org.aleksa.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin(origins = {"http://localhost:5500"}, allowedHeaders = "*")
public class StaticController {
    
    @GetMapping("/favicon.ico")
    @ResponseBody
    public void returnNoFavicon() {
        // Return empty response to avoid 500 error
    }
    
    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<Map<String, String>> index() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "ok");
        response.put("message", "Cinema API is running");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
} 