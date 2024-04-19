package com.daniel.bookservice.controller.admin;

import com.daniel.bookservice.config.jwt.JwtService;
import com.daniel.bookservice.dto.BookDto;
import com.daniel.bookservice.dto.RegisterDto;
import com.daniel.bookservice.model.enums.Roles;
import com.daniel.bookservice.response.BaseResponse;
import com.daniel.bookservice.service.book.impl.BookServiceImpl;
import com.daniel.bookservice.service.user.impl.UserServiceImpl;
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
@RequestMapping("api/v1/admin")
@Tag(name = "Admin API", description = "Resource for admin")
public class AdminController {
    private final UserServiceImpl userService;
    private final BookServiceImpl bookService;
    private final JwtService jwtService;

    Logger logger = LoggerFactory.getLogger(AdminController.class.getName());

    public AdminController(UserServiceImpl userService, BookServiceImpl bookService, JwtService jwtService) {
        this.userService = userService;
        this.bookService = bookService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    @Operation(method = "POST", summary = "Registers a new admin", responses = {
            @ApiResponse(responseCode = "200", description = "User registered",
                    content = @Content(schema = @Schema(implementation = RegisterDto.class),
                            examples = @ExampleObject(value = "{\"status\":\"200\",\"description\":\"foodName\"}"))),
            @ApiResponse(responseCode = "400", description = "Failed to register a user")
    })
    ResponseEntity<?> register(@RequestBody RegisterDto registerDto, HttpServletRequest request){
        String requestURI = request.getRequestURI();
        logger.info(requestURI + " Endpoint was used");
        BaseResponse response = userService.adminSignUp(registerDto);
        if(response.getStatusCode() == HttpStatus.OK.value()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-book")
    @Operation(method = "POST", summary = "Adding a book", responses = {
            @ApiResponse(responseCode = "200", description = "Book added",
                    content = @Content(schema = @Schema(implementation = RegisterDto.class),
                            examples = @ExampleObject(value = "{\"status\":\"200\",\"description\":\"foodName\"}"))),
            @ApiResponse(responseCode = "400", description = "Failed to register a user")
    })
    ResponseEntity<?> addBook(@RequestBody BookDto bookDto, @RequestHeader("Authorization") String token){
        String bearerToken = token.substring(7); // Remove "Bearer " prefix

        Roles role = jwtService.extractRole(bearerToken);
        if(!role.equals(Roles.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        BaseResponse response = bookService.addBook(bookDto);
        if(response.getStatusCode() == HttpStatus.OK.value()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
