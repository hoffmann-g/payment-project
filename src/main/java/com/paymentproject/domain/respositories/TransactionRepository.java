package com.paymentproject.domain.respositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymentproject.domain.entities.Transaction;
import com.paymentproject.domain.entities.User;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

    List<Transaction> findBySender(User user);
}
