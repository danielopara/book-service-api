package com.daniel.bookservice.service.book.bookInterface;

import com.daniel.bookservice.dto.BookDto;
import com.daniel.bookservice.response.BaseResponse;

public interface BookCreationService {
    BaseResponse addBook (BookDto bookDto);
}
