package com.poc.bookstore.controller;

import com.poc.bookstore.dto.LoginDTO;
import com.poc.bookstore.dto.Response;
import com.poc.bookstore.dto.SignupDTO;
import com.poc.bookstore.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * API Endpoint to let the user to signup on the application
     * Returns JWT if signup is successful
     */
    @PostMapping("/signup")
    public ResponseEntity<Response> addNewUser(@RequestBody SignupDTO signupDTO) {
        return new ResponseEntity<>(new Response(HttpStatus.OK.value(), authService.addNewUser(signupDTO)), HttpStatus.OK);
    }

    /**
     * API Endpoint to let the user to login on the application
     * Returns JWT if login is successful
     */
    @PostMapping("/login")
    public ResponseEntity<Response> loginUserWithAccessToken(@RequestBody LoginDTO loginDTO) {
        return new ResponseEntity<>(new Response(HttpStatus.OK.value(), authService.loginUserWithAccessToken(loginDTO)), HttpStatus.OK);
    }

}
