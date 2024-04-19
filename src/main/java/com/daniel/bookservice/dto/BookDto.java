package com.daniel.bookservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDto {
    private String title;
    private String author;
    private String genre;
    private BigDecimal amount;
    private String description;
    private LocalDate publicationDate;
    private Integer quantityInStock;
}
