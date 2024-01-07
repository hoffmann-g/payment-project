package com.paymentproject.domain.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymentproject.domain.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    
}
