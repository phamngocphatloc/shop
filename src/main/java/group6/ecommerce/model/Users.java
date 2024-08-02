package group6.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    private Integer id;
    @Column (name = "FullName", columnDefinition = "nvarchar(255)")
    private String fullName;
    @Column(name = "Email", nullable = false, unique = true, length = 50)
    private String email;
    @Column(name = "Phone", nullable = false, unique = true, length = 50)
    private String phone;
    @Column(name = "Password", nullable = false)
    private String password;
    @Column (name = "avatar" , columnDefinition = "nvarchar(1000)")
    private String avatar;
    @ManyToOne
    @JoinColumn(name = "RoleId")
    private Role role;
    @Column(name = "Address", nullable = false, columnDefinition = "nvarchar(500)")
    private String address;
    @Column(name = "City", nullable = false, columnDefinition = "nvarchar(50)")
    private String city;
    @Column(name = "District", nullable = false, columnDefinition = "nvarchar(50)")
    private String district;
    @Column(name = "Ward", nullable = false, columnDefinition = "nvarchar(50)")
    private String ward;
    @OneToOne(mappedBy = "UserCart", fetch = FetchType.EAGER)
    private Cart cart;
    @OneToMany (mappedBy = "userOrder", fetch = FetchType.EAGER)
    List<Order> listOrder;
    @OneToOne (mappedBy = "userToken")
    private VerificationToken Token;

    @Column (name = "Verify")
    private boolean verify;
}
