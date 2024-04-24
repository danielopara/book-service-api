package com.daniel.bookservice.repository;

import com.daniel.bookservice.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
//    Optional<Review>  (String email);
    List<Review> findByEmail(String email);
}
