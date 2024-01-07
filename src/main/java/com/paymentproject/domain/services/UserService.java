package com.paymentproject.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymentproject.domain.dtos.UserDTO;
import com.paymentproject.domain.entities.User;
import com.paymentproject.domain.respositories.UserRepository;
import com.paymentproject.domain.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;


    public User findById(Long id){
        return this.userRepository.findUserById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User save(User user){
        return this.userRepository.save(user);
    }

    public User save(UserDTO data){
        User user = new User(data);
        return this.userRepository.save(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

}
