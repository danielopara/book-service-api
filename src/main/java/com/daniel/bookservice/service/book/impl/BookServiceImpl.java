package com.daniel.bookservice.service.book.impl;

import com.daniel.bookservice.dto.BookDto;
import com.daniel.bookservice.model.Book;
import com.daniel.bookservice.repository.BookRepository;
import com.daniel.bookservice.response.BaseResponse;
import com.daniel.bookservice.service.book.BookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public BaseResponse getBooks() {
        try{
            List<Book> allBooks = bookRepository.findAll();
            return new BaseResponse(HttpStatus.OK.value(), "All Books", allBooks, null);
        } catch (Exception e){
            e.printStackTrace();
            return new BaseResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null, e.getCause());
        }

    }

    @Override
    public BaseResponse getBookById(Long id) {
        try{
            Optional<Book> bookId = bookRepository.findById(id);
            if(!bookId.isPresent()){
                return new BaseResponse(HttpStatus.NOT_FOUND.value(),
                        "Book not found", id, "book not found");
            }
            Book book = bookId.get();
            BookDto bookDto = BookDto
                    .builder()
                    .genre(book.getGenre())
                    .title(book.getTitle())
                    .author(book.getAuthor())
                    .amount(book.getAmount())
                    .description(book.getDescription())
                    .quantityInStock(book.getQuantityInStock())
                    .publicationDate(book.getPublicationDate())
                    .build();
            return new BaseResponse(
                    HttpServletResponse.SC_OK,
                    "Book retrieved",
                    bookDto,
                    null
            );
        } catch (Exception e){
            e.printStackTrace();
            return new BaseResponse(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Error",
                    id,
                    e.getMessage()
            );
        }
    }

    @Override
    public BaseResponse getBooksByTitleContaining(String title) {
        try{
            List<Book> bookTitle = bookRepository.findByTitleContaining(title);
            if(bookTitle.isEmpty()){
                return new BaseResponse(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Book not found",
                        title,
                        "Book not found"
                );
            }
            return new BaseResponse(
                    HttpServletResponse.SC_OK,
                    "Book found",
                    bookTitle,
                    null
            );
        } catch (Exception e){
            e.printStackTrace();
            return new BaseResponse(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Error",
                    e.getMessage(),
                    e.getCause()
            );
        }
    }

    @Override
    public BaseResponse getBooksByTitle(String title) {
        try{
            List<Book> bookTitle = bookRepository.findByTitle(title);
            if(bookTitle.isEmpty()){
                return new BaseResponse(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Book not found",
                        title,
                        "Book not found"
                );
            }
            return new BaseResponse(
                    HttpServletResponse.SC_OK,
                    "Book found",
                    bookTitle,
                    null
            );
        } catch (Exception e){
            e.printStackTrace();
            return new BaseResponse(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Error",
                    e.getMessage(),
                    e.getCause()
            );
        }
    }
}
