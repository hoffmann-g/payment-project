package com.paymentproject.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.paymentproject.domain.dtos.NotificationDTO;
import com.paymentproject.domain.entities.User;
import com.paymentproject.domain.services.exceptions.ServiceStatusException;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;
    
    private String url = "https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6";
    
    public void sendNotification(User user, String message){
        String email = user.getEmail();
        NotificationDTO request = new NotificationDTO(email, message);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class);
        if(!responseEntity.getStatusCode().equals(HttpStatus.OK)){
            System.out.println("could not send notification");
            throw new ServiceStatusException("Notification service is offline");
        }

    }
}
