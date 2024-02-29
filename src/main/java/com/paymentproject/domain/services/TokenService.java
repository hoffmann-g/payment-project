package com.paymentproject.domain.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.paymentproject.domain.entities.User;
import com.paymentproject.domain.services.exceptions.ServiceStatusException;

@Service
public class TokenService {

    @Autowired
    private UserService userService;

    // @Value("${api.security.token.secret}")
    private String secret = "my-secret-token";

    private Algorithm algorithm = Algorithm.HMAC256(secret);

    public String generateToken(User user) {
        try {
            String token = JWT.create()
                    .withIssuer("payment-project")
                    .withSubject(user.getDocument())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);

            return token;

        } catch (JWTCreationException e) {
            throw new ServiceStatusException("Error while generating token");
        }
    }

    public User validateToken(String token) {
        String userLogin = JWT.require(algorithm)
            .withIssuer("payment-project")
            .build()
            .verify(token)
            .getSubject();

        return userService.findByDocument(userLogin);
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
