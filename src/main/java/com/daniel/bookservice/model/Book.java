package com.daniel.bookservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name="author", nullable = false)
    private String author;
    @Column(name = "genre", nullable = false)
    private String genre;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "ratings", nullable = false)
    private Double ratings;
    @Column(name = "publicationDate", nullable = false)
    private LocalDate publicationDate;
    @Column(name = "quantityInStock", nullable = false)
    private Integer quantityInStock;
}
