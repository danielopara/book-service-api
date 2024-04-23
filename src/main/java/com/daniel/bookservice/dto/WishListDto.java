package com.daniel.bookservice.dto;

import com.daniel.bookservice.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishListDto {
    private Set<Book> book = new HashSet<>();
}
