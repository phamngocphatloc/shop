package group6.ecommerce.service.impl;

import group6.ecommerce.Repository.ReviewRepository;
import group6.ecommerce.model.Review;
import group6.ecommerce.model.Users;
import group6.ecommerce.payload.request.ReviewRequest;
import group6.ecommerce.payload.response.ReviewResponse;
import group6.ecommerce.service.ProductService;
import group6.ecommerce.service.ReviewSerive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpls implements ReviewSerive {
    private final ProductService productService;
    private final ReviewRepository reviewRepository;
    @Override
    public ReviewResponse review(ReviewRequest request, Users userReview) {
        if (checkReview(userReview.getId(),request.getProductId())==true){
            throw new IllegalArgumentException("Bạn Đã Review Rồi");
        }
        if (productService.checkUserBuyed(userReview.getId(),request.getProductId())==false){
            throw new IllegalArgumentException("Bạn Chưa Mua Sản Phẩm");
        }
        Review review = new Review();
        review.setUserReview(userReview);
        review.setReview(request.getReview());
        review.setProductReview(productService.findById(request.getProductId()));
        review.setRating(request.getRating());
        return new ReviewResponse(reviewRepository.save(review));
    }

    private Boolean checkReview (int userId, int productId){
        return reviewRepository.checkReview(userId,productId)==1?true:false;
    }
}
