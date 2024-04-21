package com.daniel.bookservice.dto;

import jakarta.validation.constraints.DecimalMax;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private String review;
    @DecimalMax("5.0")
    private Double rating;
    private String email;
    private Long bookId;
//    private String bookName;
}
