package com.daniel.bookservice.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    @Max(value = 5, message = "Rating cannot be greater than 5")
    @Min(value = 1, message = "Rating cannot be less than 0")
    private Double rating;
    private String email;
    private Long bookId;
//    private String bookName;
}
