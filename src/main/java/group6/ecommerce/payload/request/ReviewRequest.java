package group6.ecommerce.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {
    private Integer productId;
    private String review;
    private int rating;
}
