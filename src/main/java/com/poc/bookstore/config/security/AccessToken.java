package com.poc.bookstore.config.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.JsonObject;
import com.poc.bookstore.entity.User;
import com.poc.bookstore.exception.NotFoundException;
import com.poc.bookstore.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.poc.bookstore.util.JwtUtil.decodeToken;
import static com.poc.bookstore.util.JwtUtil.decodeTokenPayloadToJsonObject;

@Getter
@RequiredArgsConstructor
public class AccessToken {

    private final String value;

    private final UserRepository userRepository;

    List<GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    public Integer getUserId() {
        JsonObject payloadAsJson = getPayloadAsJsonObject();
        Integer userId = Optional.of(payloadAsJson.getAsJsonPrimitive("user_id").getAsInt()
        ).orElseThrow(() -> new NotFoundException("UserId in Access Token"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id " + userId));
        return user.getId();
    }

    private JsonObject getPayloadAsJsonObject() {
        DecodedJWT decodedJWT = decodeToken(value);
        return decodeTokenPayloadToJsonObject(decodedJWT);
    }

}
