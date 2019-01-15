package com.task.zenreadsservice.feedback.repository;

import com.task.zenreadsservice.feedback.model.Feedback;
import com.task.zenreadsservice.feedback.model.Rating;
import com.task.zenreadsservice.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {

    List<Feedback> findAllByUserAndRate(User user, Rating rate);
    List<Feedback> findAllByUser(User user);

    @Query("SELECT f2.book.id from feedback f2 " +
            "LEFT JOIN books b on f2.book = b.id " +
            "LEFT JOIN users u on f2.user = u.id " +
            "WHERE f2.rate =:rating " +
            "GROUP BY  f2.book.id " +
            "ORDER BY COUNT(f2.book.id) DESC")
    List<UUID> findTopBooksByRate(@Param("rating") Rating rating);

    @Query("SELECT f2.book.id from feedback f2 " +
            "LEFT JOIN books b on f2.book = b.id " +
            "LEFT JOIN users u on f2.user = u.id " +
            "WHERE f2.rate =:rating and NOT ( f2.user =:user) " +
            "GROUP BY  f2.book.id " +
            "ORDER BY COUNT(f2.book.id) DESC")
    List<Feedback> findTopBooksRatedByOtherUsers(@Param("rating") Rating rating,
                                                 @Param("user") User user);


}
