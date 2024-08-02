package group6.ecommerce.service.impl;

import group6.ecommerce.Repository.NotificationRepository;
import group6.ecommerce.Repository.UserRepository;
import group6.ecommerce.model.Notification;
import group6.ecommerce.model.Users;
import group6.ecommerce.payload.request.NotificationRequest;
import group6.ecommerce.payload.response.NotificationResponse;
import group6.ecommerce.service.NotificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpls implements NotificationService {
    private final NotificationRepository notificationRepository;

    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;



    public void createNotification(String message, int userId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user id"));
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setSeen(false);
        notification.setUser(user);
        notificationRepository.save(notification);
    }

    public void markNotificationAsSeen(Integer notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new IllegalArgumentException("Invalid notification id"));
        notification.setSeen(true);
        notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void sendNotificationToUser(NotificationRequest notification) {
        // Tạo một đối tượng Notification mới
        Notification notificationSave = new Notification();

        // Tìm và thiết lập đối tượng User cho Notification từ UserRepository
        Users user = userRepository.findById(notification.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + notification.getUserId()));
        notificationSave.setUser(user);

        // Không nên thiết lập ID trực tiếp nếu bạn sử dụng @GeneratedValue
        // notificationSave.setId(1);

        // Thiết lập các trường khác cho Notification
        notificationSave.setSeen(false);
        notificationSave.setMessage(notification.getMessage());
        Notification notificationSaved = notificationRepository.save(notificationSave);
        // Gửi thông báo tới user sử dụng messagingTemplate
        System.out.println("userId:" + notification.getUserId().toString());
        messagingTemplate.convertAndSendToUser(
                notification.getUserId().toString(), // Đích đến của người dùng (dựa trên phiên người dùng).
                "/topic/notifications", // Chủ đề đích mà client đăng ký.
                new NotificationResponse(notificationSaved) // Dữ liệu thông báo.
        );
    }

    @Override
    public List<NotificationResponse> getNotiUsserToDayByUserId(int UserId) {
        List<NotificationResponse> response = new ArrayList<>();
        notificationRepository.getNotiUsserToDayByUserId(UserId).stream().forEach(item ->{
            response.add(new NotificationResponse(item));
        });
        return response;
    }
}
