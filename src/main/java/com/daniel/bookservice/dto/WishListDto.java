package com.daniel.bookservice.dto;

import com.daniel.bookservice.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishListDto {
    private List<Book> book = new ArrayList<>();
}
