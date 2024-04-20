package com.daniel.bookservice.repository;

import com.daniel.bookservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
//    @Query("SELECT b FROM Book b WHERE b.title LIKE %?1%")
    List<Book> findByTitleContaining(String title);
    List<Book> findByTitle(String title);
}
