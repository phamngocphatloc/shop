package group6.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = "Cart_Details")
@Data
@Getter
@Setter
@RequiredArgsConstructor
public class Cart_Details {
    @Id
    @Column (name = "CartDetailsId")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Column (name = "Quantity")
    private int amount;
    @Column (name = "Size", columnDefinition = "nvarchar(255)")
    private String size;
    @Column (name = "Color", columnDefinition =  ("nvarchar(255)"))
    private String Color;
    @ManyToOne
    @JoinColumn (name = "CartId")
    private Cart ItemCart;
    @ManyToOne
    @JoinColumn (name = "ProductId")
    Product product;

    public Cart_Details(int amount, String size, String Color, Cart itemCart, Product product) {
        this.amount = amount;
        this.size = size;
        this.Color = Color;
        ItemCart = itemCart;
        this.product = product;
    }
}
