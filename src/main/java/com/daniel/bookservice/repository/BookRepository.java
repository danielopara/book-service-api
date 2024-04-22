package com.daniel.bookservice.repository;

import com.daniel.bookservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContaining(String title);
    List<Book> findByTitle(String title);
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.book.id = :id")
    Double findAverageRatingsById(@Param("id") Long bookId);
}
