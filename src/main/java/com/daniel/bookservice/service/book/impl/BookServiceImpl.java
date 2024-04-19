package com.daniel.bookservice.service.book.impl;

import com.daniel.bookservice.dto.BookDto;
import com.daniel.bookservice.model.Book;
import com.daniel.bookservice.repository.BookRepository;
import com.daniel.bookservice.response.BaseResponse;
import com.daniel.bookservice.service.book.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BaseResponse addBook(BookDto bookDto) {
        BaseResponse response = new BaseResponse();
        try{
            Book book = new Book();
            book.setTitle(bookDto.getTitle());
            book.setAmount(bookDto.getAmount());
            book.setAuthor(bookDto.getAuthor());
            book.setGenre(bookDto.getGenre());
            book.setDescription(bookDto.getDescription());
            book.setPublicationDate(bookDto.getPublicationDate());
            book.setQuantityInStock(bookDto.getQuantityInStock());

            bookRepository.save(book);
            response.setDescription("Book Added");
            response.setData(bookDto);
            response.setStatusCode(HttpStatus.OK.value());

            return response;
        } catch (Exception e){
            response.setDescription("Error");
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setError(e.getMessage());

            return response;
        }
    }
}
