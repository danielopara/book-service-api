package com.daniel.bookservice.service.book;

import com.daniel.bookservice.dto.BookDto;
import com.daniel.bookservice.response.BaseResponse;

public interface BookService {
    BaseResponse addBook (BookDto bookDto);
}
