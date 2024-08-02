package group6.ecommerce.payload.response;


import group6.ecommerce.model.Notification;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NotificationResponse {
    private String message;

    private boolean seen;

    private UserDetailsResponse user;
    private Date create_at;

    public NotificationResponse (Notification notification){
        this.message = notification.getMessage();
        this.seen = notification.isSeen();
        this.user = new UserDetailsResponse(notification.getUser());
        this.create_at = notification.getCreate_at();
    }
}
