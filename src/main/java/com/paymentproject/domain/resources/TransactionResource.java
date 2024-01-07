package com.paymentproject.domain.resources;

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

@RestController
@RequestMapping("/transactions")
public class TransactionResource {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    private ResponseEntity<Transaction> save(@RequestBody TransactionDTO transactionDTO){
        Transaction transaction = transactionService.save(transactionDTO);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
}
