package com.example.FireBasePushNotification.service;


import com.example.FireBasePushNotification.model.NotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
@Service
public class NotificationService {

    private Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private FCMService fcmService;

    public NotificationService(FCMService fcmService) {
        this.fcmService = fcmService;
    }


    public void sendPushNotificationToToken(NotificationRequest request) {
        try {
            fcmService.sendMessageToToken(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

}