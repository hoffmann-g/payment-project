package com.paymentproject.domain.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.paymentproject.domain.dtos.TransactionDTO;
import com.paymentproject.domain.entities.Transaction;
import com.paymentproject.domain.entities.User;
import com.paymentproject.domain.entities.enums.UserType;
import com.paymentproject.domain.respositories.TransactionRepository;
import com.paymentproject.domain.services.exceptions.ResourceNotFoundException;
import com.paymentproject.domain.services.exceptions.TransactionAuthorizationException;
import com.paymentproject.domain.services.exceptions.TransactionValidationException;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private RestTemplate restTemplate;
    
    private String validatorAddress = "https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc";

    public Transaction save(TransactionDTO transactionDTO){
        User sender = this.userService.findById(transactionDTO.senderId());
        User receiver = this.userService.findById(transactionDTO.receiverId());
        BigDecimal amount = transactionDTO.amount();

        validateTransaction(sender, amount);
        authorizeTransaction();

        Transaction transaction = new Transaction(null,  amount, sender, receiver, LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        userService.save(sender);
        userService.save(receiver);

        notificationService.sendNotification(sender, "Your transaction was successfully done to " + receiver.getFirstName() + ".");
        notificationService.sendNotification(receiver, "You recieved " + amount + " from " + sender.getFirstName() + ".");

        return transactionRepository.save(transaction);
    }

    public Transaction findById(Long id){
        return this.transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
    }

    public List<Transaction> findUserTransactions(Long id){
        List<Transaction> transactionlList = transactionRepository.findAll();
        List<Transaction> filteredList = transactionlList.stream().filter(t -> t.getSender().getId().equals(id)).collect(Collectors.toList());
        return filteredList;
    }

    private void authorizeTransaction() {
        ResponseEntity<Map<String, Object>> authResponse = restTemplate.exchange(
                validatorAddress,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        Map<String, Object> responseBody = authResponse.getBody();

        if (!(authResponse.getStatusCode() == HttpStatus.OK && responseBody != null && 
        String.valueOf(responseBody.get("message")).equalsIgnoreCase("Autorizado"))) {
            throw new TransactionAuthorizationException("Transaction denied");
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
