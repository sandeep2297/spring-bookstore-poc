package com.poc.bookstore.service;

import com.poc.bookstore.dto.LoginDTO;
import com.poc.bookstore.dto.SignupDTO;

public interface AuthService {

    String addNewUser(SignupDTO signupDTO);

    String loginUserWithAccessToken(LoginDTO loginDTO);
}
