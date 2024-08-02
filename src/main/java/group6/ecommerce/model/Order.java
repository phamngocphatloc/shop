package group6.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Table (name = "Orders")
@Data
@Getter
@Setter
public class Order {
    @Id
    @Column (name = "OrderId")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int Id;
    @Column (name = "FullName", columnDefinition = "nvarchar(255)")
    private String fullName;
    @Column (name = "phone")
    private String phone;
    @Column (name = "email", columnDefinition = "nvarchar(255)")
    private String email;
    @Column (name = "OrderDate")
    Date orderDate;
    @Column (name = "Coupon")
    private String coupon;
    @Column (name = "Payment")
    private String payment;
    @Column (name = "Status")
    private String Status;
    @Column (name = "note", columnDefinition = "nvarchar(255)")
    private String note;
    @ManyToOne
    @JoinColumn (name = "UserId")
    private Users userOrder;
    @OneToMany (mappedBy = "ItemOrder", fetch = FetchType.EAGER)
    Map<String,Order_Details> listItems = new HashMap<>();

    @Column(name = "created_at")
    private java.util.Date createdAt = new java.util.Date();
    @Column (name = "shipping")
    private int shipping;

    @Column (name = "address", columnDefinition = "nvarchar(500)")
    private String address;
        public int getTotalPrice (){
        AtomicInteger price = new AtomicInteger();
        listItems.values().stream().forEach(o -> {
            price.addAndGet(o.getProduct().getPrice() * o.getAmount());
        });
        price.addAndGet(shipping);
        return price.get();
    }
}
