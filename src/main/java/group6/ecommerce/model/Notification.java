package group6.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Notification {
    @Id
    @Column (name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column (columnDefinition = "nvarchar(255)")
    private String message;

    private boolean seen;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    private Date create_at = new Date();
}
