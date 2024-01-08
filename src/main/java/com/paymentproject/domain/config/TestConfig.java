package com.paymentproject.domain.config;

import java.math.BigDecimal;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.paymentproject.domain.entities.User;
import com.paymentproject.domain.respositories.UserRepository;
import com.paymentproject.domain.entities.enums.UserType;

@Configuration
public class TestConfig implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        
        User u1 = new User(null, "Bob", "Brown", "04210939005", "bob@gmail.com", "password123", new BigDecimal(100), UserType.COMMON);
        User u2 = new User(null, "Maria", "Dunha", "68962061015", "maria@gmail.com", "password123", new BigDecimal(50), UserType.COMMON );
        User u3 = new User(null, "Mr.", "Cadela", "97842257000", "cadela@sigmamales.com", "password123", new BigDecimal(6000), UserType.COMMON );

        userRepository.saveAll(Arrays.asList(u1, u2, u3));
    }


}
