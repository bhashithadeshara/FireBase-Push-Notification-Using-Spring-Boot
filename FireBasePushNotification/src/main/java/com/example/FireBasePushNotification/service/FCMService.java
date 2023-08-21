package com.example.FireBasePushNotification.service;


import com.example.FireBasePushNotification.model.NotificationRequest;
import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

@Service
public class FCMService {
    private Logger logger = LoggerFactory.getLogger(FCMService.class);


    public void sendMessageToToken(NotificationRequest request)
            throws InterruptedException, ExecutionException {

        AndroidConfig androidConfig = getAndroidConfig(request.getTopic());
        ApnsConfig apnsConfig = getApnsConfig(request.getTopic());

        Message message = Message.builder().
                setToken(request.getToken()).
                setNotification(Notification.builder()
                        .setTitle(request.getTitle())
                        .setBody(request.getMessage())
                        .build())
                .putData("content", request.getTitle())
                .putData("body", request.getMessage())
                .setApnsConfig(apnsConfig)
                .setAndroidConfig(androidConfig)
                .build();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(message);
        String response = sendAndGetResponse(message);
        logger.info("Sent message to token. Device token: " + request.getToken() + ", " + response+ " msg "+jsonOutput);
    }

    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }


    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder()
                        .setSound(NotificationProperties.SOUND.getValue())
                        .setColor(NotificationProperties.COLOR.getValue())
                        .setTag(topic).build()).build();
    }
    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }
//    private Message getPreconfiguredMessageToToken(NotificationRequest request) {
//        return getPreconfiguredMessageBuilder(request).setToken(request.getToken())
//                .build();
//    }
//    private Message getPreconfiguredMessageWithoutData(NotificationRequest request) {
//        return getPreconfiguredMessageBuilder(request).setTopic(request.getTopic())
//                .build();
//    }
//    private Message getPreconfiguredMessageWithData(Map<String, String> data, NotificationRequest request) {
//        return getPreconfiguredMessageBuilder(request).putAllData(data).setToken(request.getToken())
//                .build();
//    }
//    private Message.Builder getPreconfiguredMessageBuilder(NotificationRequest request) {
//        AndroidConfig androidConfig = getAndroidConfig(request.getTopic());
//        ApnsConfig apnsConfig = getApnsConfig(request.getTopic());
//        Message.Builder builder = Message.builder()
//                .setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).setNotification(
//                        new Notification(request.getTitle(), request.getMessage()));
//        return builder;
//    }

    private enum NotificationProperties {
        SOUND("default"),
        COLOR("#FFFF00");

        private String value;

        NotificationProperties(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
}