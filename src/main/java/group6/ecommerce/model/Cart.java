package group6.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Table (name = "Cart")
@Data
@Getter
@Setter
public class Cart {
    @Id
    @Column (name = "CartId")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int cartId;
    @OneToOne
    @JoinColumn (name = "UserId")
    private Users UserCart;
    @OneToMany (mappedBy = "ItemCart",
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    Map<String,Cart_Details> listItems;

    public int getTotalPrice (){
        AtomicInteger price = new AtomicInteger();
        listItems.values().stream().forEach(o -> {
            price.addAndGet(o.getProduct().getPrice() * o.getAmount());
        });
        return price.get();
    }
}
