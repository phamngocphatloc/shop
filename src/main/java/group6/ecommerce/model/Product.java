package group6.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table (name = "Product")
@Data
@Getter
@Setter
public class Product {
    @Id
    @Column (name = "ProductId")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Column (name = "ProductName",columnDefinition = "nvarchar(255)")
    private String name;
    @Column (name = "ProductPrice")
    int price;
    @Column (name = "Dimension")
    private int dimension;
    @Column (name = "Weight")
    private int weight;
    @Column (name = "Material",columnDefinition = "nvarchar(255)")
    private String material;
    @ManyToOne
    @JoinColumn (name = "CategoryId")
    private Category category;
    @Column (name = "ImageUrls", length = 500)
    private String imageUrls;
    @ManyToOne
    @JoinColumn (name = "TypeId")
    private Type type;
    @Column (name = "Description",columnDefinition = "nvarchar(255)")
    private String description;
    @OneToMany(mappedBy = "Products", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductDetails> listProductDetails;
    @OneToMany (mappedBy = "productReview",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Review> reviews;
}
