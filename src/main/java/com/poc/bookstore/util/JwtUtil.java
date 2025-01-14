package com.poc.bookstore.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.poc.bookstore.entity.User;
import com.poc.bookstore.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

public class JwtUtil {

    public static String generateAccessToken(User user, String secretKey, long jwtExpiration) {
        Claims claims = Jwts.claims();
        claims.put("user_id", user.getId());
        claims.put("user_name", user.getUserName());
        return Jwts.builder().setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(secretKey), SignatureAlgorithm.HS256).compact();
    }

    private static Key getSigningKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static DecodedJWT decodeToken(String value) {
        if (Objects.isNull(value)) {
            throw new InvalidTokenException("Token has not been provided");
        }

        try {
            return JWT.decode(value);
        } catch (Exception exception) {
            throw new InvalidTokenException("Invalid JWT " + exception.getMessage());
        }
    }

    public static void verifyPayload(DecodedJWT decodedJWT) {
        JsonObject payloadAsJson = decodeTokenPayloadToJsonObject(decodedJWT);
        if (hasTokenExpired(payloadAsJson)) {
            throw new InvalidTokenException("Access Token has expired");
        }
    }

    public static JsonObject decodeTokenPayloadToJsonObject(DecodedJWT decodedJWT) {
        try {
            String payloadAsString = decodedJWT.getPayload();
            return new Gson().fromJson(new String(Base64.getDecoder().decode(payloadAsString), StandardCharsets.UTF_8),
                    JsonObject.class);
        } catch (Exception exception) {
            throw new InvalidTokenException("Invalid JWT " + exception.getMessage());
        }
    }

    private static boolean hasTokenExpired(JsonObject payloadAsJson) {
        Instant expirationDateTime = extractExpirationDateTime(payloadAsJson);
        return Instant.now().isAfter(expirationDateTime);
    }

    private static Instant extractExpirationDateTime(JsonObject payloadAsJson) {
        try {
            return Instant.ofEpochSecond(payloadAsJson.get("exp").getAsLong());
        } catch (NullPointerException exception) {
            throw new InvalidTokenException("There is no 'exp' claim in the token payload");
        }
    }

    public static void verifySignature(String token, String secretKey) {
        try {
            Jwts.parser().setSigningKey(getSigningKey(secretKey)).parseClaimsJws(token);
        } catch (SignatureVerificationException exception) {
            throw new InvalidTokenException("Access Token has invalid signature " + exception.getMessage());
        }
    }

    public static String extractAuthorizationHeaderAsString(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (Objects.isNull(authHeader)) {
            throw new InvalidTokenException("Access Token is not provided with the Authorization");
        }

        if (authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        } else {
            return authHeader;
        }

    }

}
