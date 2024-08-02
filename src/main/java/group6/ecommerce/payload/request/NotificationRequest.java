package group6.ecommerce.payload.request;

import group6.ecommerce.model.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NotificationRequest {
    private String message;
    private Integer userId;

}
