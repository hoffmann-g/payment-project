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

    public User save(UserDTO userDTO){
        User user = new User();

        user.setFirstName(userDTO.firstName());
        user.setLastName(userDTO.lastName());
        user.setDocument(userDTO.document());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());
        user.setBalance(userDTO.balance());
        user.setUserType(userDTO.userType());

        return this.userRepository.save(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

}
