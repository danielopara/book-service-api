package com.daniel.bookservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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
    @Max(value = 5, message = "Rating cannot be greater than 5")
    @Min(value = 1, message = "Rating cannot be less than 0")
    private Double rating;
    @Column(nullable = false, name = "email")
    private String email;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @JsonFormat(pattern = "dd-mm-yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdAT;
}
