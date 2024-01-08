package com.paymentproject.domain.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paymentproject.domain.dtos.UserDTO;
import com.paymentproject.domain.entities.Transaction;
import com.paymentproject.domain.entities.User;
import com.paymentproject.domain.services.TransactionService;
import com.paymentproject.domain.services.UserService;
import com.paymentproject.domain.services.exceptions.DatabaseException;

@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;
    
    @PostMapping
    public ResponseEntity<User> save(@RequestBody UserDTO userDTO){
        try {
            return new ResponseEntity<>(userService.save(userDTO), HttpStatus.CREATED);
        } catch (Exception e){
            throw new DatabaseException(null);
        }
        
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        List<User> userList = userService.findAll();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        User user = userService.findById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<Transaction>> findUserTransactions(@PathVariable Long id){
        List<Transaction> transactionList = transactionService.findUserTransactions(id);
        return new ResponseEntity<>(transactionList, HttpStatus.OK);
    }
}
