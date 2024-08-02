package group6.ecommerce.Repository;

import group6.ecommerce.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {
    @Query (value = "select CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END from review where user_id = ?1 and product_id =?2",nativeQuery = true)
    public Integer checkReview (int userId, int productId);
}
