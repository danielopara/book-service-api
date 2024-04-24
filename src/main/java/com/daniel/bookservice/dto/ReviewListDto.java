package com.daniel.bookservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewListDto {
    private String email;
    private String review;
    private Double rating;
    private String bookTitle;
    @JsonFormat(pattern = "dd-mm-yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdAT;
}
