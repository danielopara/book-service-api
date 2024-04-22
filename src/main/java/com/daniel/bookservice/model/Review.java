package com.daniel.bookservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, name = "review")
    private String review;
    @Column(nullable = false, name = "rating")
    @DecimalMax("5.0")
    private Double rating;
    @Column(nullable = false, name = "email")
    private String email;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
