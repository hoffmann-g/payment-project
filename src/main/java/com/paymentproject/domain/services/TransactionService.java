package com.paymentproject.domain.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentproject.domain.dtos.TransactionDTO;
import com.paymentproject.domain.entities.Transaction;
import com.paymentproject.domain.entities.User;
import com.paymentproject.domain.entities.enums.UserType;
import com.paymentproject.domain.respositories.TransactionRepository;
import com.paymentproject.domain.services.exceptions.ServiceStatusException;
import com.paymentproject.domain.services.exceptions.TransactionAuthorizationException;
import com.paymentproject.domain.services.exceptions.TransactionValidationException;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;
    
    private String validatorAddress = "https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc";

    public Transaction save(TransactionDTO transactionDTO){
        User sender = this.userService.findById(transactionDTO.senderId());
        User reciever = this.userService.findById(transactionDTO.recieverId());
        BigDecimal value = transactionDTO.value();

        validateTransaction(sender, value);
        authorizeTransaction(sender, value);

        Transaction transaction = new Transaction(null,  value, sender, reciever, LocalDateTime.now());

        sender.getBalance().subtract(value);
        reciever.getBalance().add(value);

        userService.save(reciever);
        userService.save(reciever);

        return transactionRepository.save(transaction);
    }

    private void authorizeTransaction(User sender, BigDecimal value){
        ResponseEntity<String> authResponse = restTemplate.getForEntity(validatorAddress, String.class);
        
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(authResponse.getBody());
            if (!(authResponse.getStatusCode() == HttpStatus.OK && jsonNode.get("message").asText().equalsIgnoreCase("Aprovado"))){
            throw new TransactionAuthorizationException("Transaction denied");
        }
        } catch (Exception e){
            throw new ServiceStatusException("External authorization service is offline ");
        }
    }


    private void validateTransaction(User sender, BigDecimal amount){
        if (sender.getUserType() == UserType.MERCHANT){
            throw new TransactionValidationException("'Merchant' user type is not allowed to make transactions");
        }
        if (sender.getBalance().compareTo(amount) < 0){
            throw new TransactionValidationException("User's current balance is insuficient to complete transaction");
        }
    }
}
