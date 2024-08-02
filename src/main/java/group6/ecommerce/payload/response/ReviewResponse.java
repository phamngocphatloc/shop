package group6.ecommerce.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import group6.ecommerce.model.Product;
import group6.ecommerce.model.Review;
import group6.ecommerce.model.Users;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ReviewResponse {
    private int Id;
    private UserDetailsResponse userReview;
    private String review;
    private int rating;

    public ReviewResponse (Review review){
        this.Id = review.getId();
        this.userReview =  new UserDetailsResponse(review.getUserReview());
        this.review = review.getReview();
        this.rating = review.getRating();
    }
}
