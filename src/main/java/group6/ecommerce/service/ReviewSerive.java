package group6.ecommerce.service;

import group6.ecommerce.model.Users;
import group6.ecommerce.payload.request.ReviewRequest;
import group6.ecommerce.payload.response.ReviewResponse;
import org.apache.catalina.User;

public interface ReviewSerive {
    public ReviewResponse review (ReviewRequest request , Users userReview);
}
