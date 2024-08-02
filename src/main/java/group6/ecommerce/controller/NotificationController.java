package group6.ecommerce.controller;

import group6.ecommerce.model.Users;
import group6.ecommerce.payload.request.NotificationRequest;
import group6.ecommerce.payload.response.HttpResponse;
import group6.ecommerce.payload.response.NotificationResponse;
import group6.ecommerce.service.NotificationService;
import group6.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final UserService userService;
    @PostMapping("/sendNotification")
    public ResponseEntity<HttpResponse> sendNotificationToUser(@RequestBody NotificationRequest notification) {
        notificationService.sendNotificationToUser(notification);
        return ResponseEntity.ok(new HttpResponse(200,"send notification successfully",null));
    }

    @GetMapping ("/notification")
    public ResponseEntity<HttpResponse> getNotification (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users principal = (Users) authentication.getPrincipal();
        Users userLogin = userService.findById(principal.getId());
        List<NotificationResponse> response = notificationService.getNotiUsserToDayByUserId(userLogin.getId());
        return ResponseEntity.ok(new HttpResponse(HttpStatus.OK.value(),"success",response));
    }
}
