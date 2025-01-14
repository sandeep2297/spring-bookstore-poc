package com.poc.bookstore.service.impl;

import com.poc.bookstore.dto.LoginDTO;
import com.poc.bookstore.dto.SignupDTO;
import com.poc.bookstore.entity.User;
import com.poc.bookstore.exception.NotFoundException;
import com.poc.bookstore.exception.UnAuthorizedException;
import com.poc.bookstore.repository.UserRepository;
import com.poc.bookstore.service.AuthService;
import com.poc.bookstore.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service("authService")
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    @Transactional
    public String addNewUser(SignupDTO signupDTO) {
        Optional<User> userOptional = userRepository.findByUserName(signupDTO.getUserName());
        if (userOptional.isPresent()) {
            throw new DuplicateKeyException("User with " + signupDTO.getUserName() + " has an existing account, " +
                    "Please login with existing account");
        } else {
            User user = User.builder()
                    .userName(signupDTO.getUserName())
                    .password(bCryptPasswordEncoder.encode(signupDTO.getPassword()))
                    .fullName(signupDTO.getFullName())
                    .createdDate(new Date())
                    .build();
            return JwtUtil.generateAccessToken(userRepository.save(user), secretKey, jwtExpiration);
        }
    }


    public String loginUserWithAccessToken(LoginDTO loginDTO) {
        User user = userRepository.findByUserName(loginDTO.getUserName())
                .orElseThrow(() -> new NotFoundException("User with user name " + loginDTO.getUserName()));
        if (Boolean.FALSE.equals(matchesPassword(loginDTO.getPassword(), user.getPassword()))) {
            throw new UnAuthorizedException("Invalid Password");
        } else {
            return JwtUtil.generateAccessToken(user, secretKey, jwtExpiration);
        }
    }

    public boolean matchesPassword(String loginPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(loginPassword, encodedPassword);
    }
}