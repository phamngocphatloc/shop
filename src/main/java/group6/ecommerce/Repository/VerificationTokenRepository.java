package group6.ecommerce.Repository;

import group6.ecommerce.model.Users;
import group6.ecommerce.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Integer> {
    VerificationToken findByToken(String token);
    VerificationToken findByUserToken (Users user);
}
