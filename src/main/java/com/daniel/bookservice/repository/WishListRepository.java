package com.daniel.bookservice.repository;

import com.daniel.bookservice.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
    Optional<WishList> findByEmail(String email);
    boolean existsByEmailAndBookId(String email, Long bookId);
}
