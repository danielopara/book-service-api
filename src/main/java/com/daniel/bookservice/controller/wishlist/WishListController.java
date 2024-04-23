package com.daniel.bookservice.controller.wishlist;


import com.daniel.bookservice.model.Book;
import com.daniel.bookservice.model.Review;
import com.daniel.bookservice.model.WishList;
import com.daniel.bookservice.response.BaseResponse;
import com.daniel.bookservice.service.wishlist.impl.WishListServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/wishlist")
@Tag(name = "Wishlist API", description = "Resource for wishlist")
@CrossOrigin("*")
public class WishListController{
    private final WishListServiceImpl wishListService;
    Logger logger = LoggerFactory.getLogger(WishListController.class.getName());

    public WishListController(WishListServiceImpl wishListService) {
        this.wishListService = wishListService;
    }
    @PostMapping("/add-to-wishlist")
    @Operation(method = "POST", summary = "Adds to wishlist", responses = {
            @ApiResponse(responseCode = "200", description = "Wishlist added",
                    content = @Content(schema = @Schema(implementation = WishList.class))),
            @ApiResponse(responseCode = "400", description = "Failed to add book")
    })
    ResponseEntity<?> addReview(@RequestBody WishBookRequest wishBookRequest, HttpServletRequest request){
        String requestURI = request.getRequestURI();
        logger.info(requestURI + " Endpoint was used");
        BaseResponse response = wishListService.wishABook(wishBookRequest.getBookId(),
                wishBookRequest.getEmail());
        if(response.getStatusCode() == HttpServletResponse.SC_OK){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/wish/{email}")
    @Operation(method = "GET", summary = "Retrieving wishlist for a user", responses = {
            @ApiResponse(responseCode = "200", description = "Books retrieved",
                    content = @Content(schema = @Schema(implementation = WishList.class))),
            @ApiResponse(responseCode = "400", description = "Failed to retrieve wishlist")
    })
    ResponseEntity<?> getWishListByEmail(HttpServletRequest request, @PathVariable String email){
        String requestURI = request.getRequestURI();
        BaseResponse response = wishListService.getWishListByEmail(email);
        logger.info(requestURI + " Endpoint was used");
        if(response.getStatusCode() == HttpServletResponse.SC_OK){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}

@Data
class WishBookRequest {
    private Long bookId;
    private String email;
}
