package com.paymentproject.domain.config;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.paymentproject.domain.entities.Transaction;
import com.paymentproject.domain.entities.User;
import com.paymentproject.domain.respositories.TransactionRepository;
import com.paymentproject.domain.services.AuthenticationService;
import com.paymentproject.domain.entities.enums.UserType;

@Configuration
public class TestConfig implements CommandLineRunner {
    
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void run(String... args) throws Exception {
        
        User u1 = new User(null, "Bob", "Brown", "04210939005", "bob@gmail.com", "password123", new BigDecimal(100), UserType.COMMON);
        User u2 = new User(null, "Maria", "Dunha", "68962061015", "maria@gmail.com", "password123", new BigDecimal(50), UserType.COMMON );
        User u3 = new User(null, "Mr.", "Cadela", "97842257000", "cadela@sigmamales.com", "password123", new BigDecimal(6000), UserType.COMMON );

        authenticationService.register(u1);
        authenticationService.register(u2);
        authenticationService.register(u3);

        Transaction transaction = new Transaction(null, new BigDecimal(30.), u1, u2, LocalDateTime.now());

        transactionRepository.save(transaction);
    }


}
