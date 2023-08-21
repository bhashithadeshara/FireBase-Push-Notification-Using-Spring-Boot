package com.example.FireBasePushNotification.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
@Service
public class FCMInitializer {

    @Value("${app.firebase-config}")
    private String firebaseConfigPath;
    Logger logger = LoggerFactory.getLogger(FCMInitializer.class);
    private FirebaseApp firebaseApp;

    @PostConstruct
    public void initialize() {
        try {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())).build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                logger.info("Firebase application has been initialized");
            }
            else {
                FirebaseApp.getInstance();
                logger.info("Firebase application has been fetched");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}