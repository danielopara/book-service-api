package com.daniel.bookservice.controller.book;


import com.daniel.bookservice.model.Book;
import com.daniel.bookservice.response.BaseResponse;
import com.daniel.bookservice.service.book.impl.BookServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/book")
@Tag(name = "Book API", description = "Resource for book")
@CrossOrigin("*")
public class BookController {
    private final BookServiceImpl bookService;
    Logger logger = LoggerFactory.getLogger(BookController.class.getName());

    public BookController(BookServiceImpl bookService) {
        this.bookService = bookService;
    }
    @GetMapping("")
    @Operation(method = "GET", summary = "Retrieving all books", responses = {
            @ApiResponse(responseCode = "200", description = "All books",
                    content = @Content(schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "400", description = "Failed to retrieve all books")
    })
    ResponseEntity<?> getAllBooks(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        BaseResponse response = bookService.getBooks();
        logger.info(requestURI + " Endpoint was used");
        if(response.getStatusCode() == HttpStatus.OK.value()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{id}")
    @Operation(method = "GET", summary = "Retrieving a book by id", responses = {
            @ApiResponse(responseCode = "200", description = "Book retrieved",
                    content = @Content(schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "400", description = "Failed to retrieve book")
    })
    ResponseEntity<?> getBookById(HttpServletRequest request, @PathVariable Long id){
        String requestURI = request.getRequestURI();
        BaseResponse response = bookService.getBookById(id);
        logger.info(requestURI + " Endpoint was used");
        if(response.getStatusCode() == HttpStatus.OK.value()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
