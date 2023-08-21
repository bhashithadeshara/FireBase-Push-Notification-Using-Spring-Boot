package com.example.FireBasePushNotification.controller;

import com.example.FireBasePushNotification.model.NotificationRequest;
import com.example.FireBasePushNotification.model.NotificationResponse;
import com.example.FireBasePushNotification.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.FireBasePushNotification.service.NotificationService;

@RestController
public class NotificationController {


    private NotificationService NotificationService;

    public NotificationController(NotificationService NotificationService) {
        this.NotificationService = NotificationService;
    }

    @PostMapping("/notification/token")
    public ResponseEntity sendTokenNotification(@RequestBody NotificationRequest request) {
        NotificationService.sendPushNotificationToToken(request);
        System.out.println("princr");
        return new ResponseEntity<>(new NotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }

}