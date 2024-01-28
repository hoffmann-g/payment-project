package com.paymentproject.domain.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.paymentproject.domain.dtos.LoginDTO;
import com.paymentproject.domain.dtos.RegisterDTO;
import com.paymentproject.domain.entities.User;
import com.paymentproject.domain.entities.enums.UserType;
import com.paymentproject.domain.services.exceptions.DatabaseException;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findByDocument(username);
    }

    public String login(LoginDTO loginDTO) {
        var userPasswordToken = new UsernamePasswordAuthenticationToken(loginDTO.login(), loginDTO.password());
        var auth = this.authenticationManager.authenticate(userPasswordToken);

        return tokenService.generateToken((User) auth.getPrincipal());
    }

    public void register(RegisterDTO registerDTO) {
        if (userService.existsByDocument(registerDTO.document())) {
            throw new DatabaseException("User already exists");
        } else {
            String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
            User user = new User(
                    null,
                    registerDTO.firstName(),
                    registerDTO.lastName(),
                    registerDTO.document(),
                    registerDTO.email(),
                    encryptedPassword,
                    new BigDecimal(0),
                    UserType.enumValueOf(registerDTO.userType()));

            userService.save(user);
        }
        
        ////////////////////
    }

    public void register(User user) {
        if (userService.existsByDocument(user.getDocument())) {
            throw new DatabaseException("User already exists");
        } else {
            String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
            user.setPassword(encryptedPassword);

            userService.save(user);
        }
    }
}
