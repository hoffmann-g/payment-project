package com.paymentproject.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymentproject.domain.entities.Transaction;
import com.paymentproject.domain.entities.User;
import com.paymentproject.domain.respositories.TransactionRepository;
import com.paymentproject.domain.respositories.UserRepository;
import com.paymentproject.domain.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public User findById(Long id){
        return this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User save(User user){
        return this.userRepository.save(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public List<Transaction> findUserTransactions(Long id){
        return transactionRepository.findBySender(findById(id));
    }

    public User findByDocument(String document){
        return userRepository.findByDocument(document).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public boolean existsByDocument(String document){
        return userRepository.existsByDocument(document);
    }

    public boolean existsById(Long id){
        return userRepository.existsById(id);
    }
}
