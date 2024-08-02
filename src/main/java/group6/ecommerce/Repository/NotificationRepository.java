package group6.ecommerce.Repository;

import group6.ecommerce.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Integer> {
    @Query (value = "SELECT *\n" +
            "FROM notification\n" +
            "WHERE CONVERT(date, create_at) = CONVERT(date, GETDATE()) and user_id = ?1",nativeQuery = true)
    public List<Notification> getNotiUsserToDayByUserId (int UserId);
}
