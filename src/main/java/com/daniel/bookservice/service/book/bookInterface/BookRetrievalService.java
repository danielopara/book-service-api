package com.daniel.bookservice.service.book.bookInterface;

import com.daniel.bookservice.response.BaseResponse;

public interface BookRetrievalService {
    BaseResponse getBooks();
    BaseResponse getBookById(Long id);
    BaseResponse getBooksByTitleContaining(String title);
    BaseResponse getBooksByTitle(String title);
}
