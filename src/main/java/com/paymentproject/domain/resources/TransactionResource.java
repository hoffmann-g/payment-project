package com.paymentproject.domain.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paymentproject.domain.dtos.TransactionDTO;
import com.paymentproject.domain.entities.Transaction;
import com.paymentproject.domain.services.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/transactions")
public class TransactionResource {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> save(@RequestBody TransactionDTO transactionDTO){
        return new ResponseEntity<>(transactionService.save(transactionDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> findAll() {
        List<Transaction> transactions = transactionService.findAll();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
    
}
