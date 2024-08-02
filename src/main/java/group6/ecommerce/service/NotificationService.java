package group6.ecommerce.service;

import group6.ecommerce.model.Notification;
import group6.ecommerce.payload.request.NotificationRequest;
import group6.ecommerce.payload.response.NotificationResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

public interface NotificationService {
    public void createNotification(String message, int userId);
    public void markNotificationAsSeen(Integer notificationId);
    public void sendNotificationToUser(NotificationRequest notification);
    public List<NotificationResponse> getNotiUsserToDayByUserId (int UserId);
}
