package group6.ecommerce.payload.request;

import group6.ecommerce.model.Order;
import group6.ecommerce.model.Users;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
public class OrderRequest {
    Date orderDate = new Date(System.currentTimeMillis());
    private String coupon = "";
    private String  payment;
    private Users userOrder;
    private String fullName;
    private String phone;
    private String email;
    private String note;
    private String address;
    private int shipping;

    public Order getOrder (){
        Order order = new Order();
        order.setOrderDate(this.orderDate);
        order.setUserOrder(this.userOrder);
        order.setCoupon(this.coupon);
        order.setPayment(this.payment);
        order.setFullName(this.fullName);
        order.setPhone(this.phone);
        order.setEmail(this.email);
        order.setNote(this.note);
        order.setShipping(this.shipping);
        order.setAddress(this.address);
        order.setStatus("pending");
        return  order;
    }
}
