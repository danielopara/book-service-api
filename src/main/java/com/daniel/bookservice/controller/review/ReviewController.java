package com.daniel.bookservice.controller.review;

import com.daniel.bookservice.dto.BookDto;
import com.daniel.bookservice.dto.ReviewDto;
import com.daniel.bookservice.dto.ReviewListDto;
import com.daniel.bookservice.model.Book;
import com.daniel.bookservice.model.Review;
import com.daniel.bookservice.response.BaseResponse;
import com.daniel.bookservice.service.review.impl.ReviewServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/review")
@CrossOrigin("*")
@Tag(name = "Review API")
public class ReviewController {
    private final ReviewServiceImpl reviewService;
    Logger logger = LoggerFactory.getLogger(ReviewController.class.getName());

    public ReviewController(ReviewServiceImpl reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/add-review")
    @Operation(method = "POST", summary = "Adds a review", responses = {
            @ApiResponse(responseCode = "200", description = "Review added",
                    content = @Content(schema = @Schema(implementation = Review.class))),
            @ApiResponse(responseCode = "400", description = "Failed to add review")
    })
    ResponseEntity<?> addReview(@RequestBody ReviewDto reviewDto, HttpServletRequest request){
        String requestURI = request.getRequestURI();
        logger.info(requestURI + " Endpoint was used");
        BaseResponse response = reviewService.addReview(reviewDto);
        if(response.getStatusCode() == HttpServletResponse.SC_OK){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{email}")
    @Operation(method = "GET", summary = "Retrieving reviews for emails", responses = {
            @ApiResponse(responseCode = "200", description = "Review retrieved",
                    content = @Content(schema = @Schema(implementation = ReviewListDto.class))),
            @ApiResponse(responseCode = "400", description = "Failed to retrieve book")
    })
    ResponseEntity<?> getReviewsByEmail(HttpServletRequest request, @PathVariable String email){
        String requestURI = request.getRequestURI();
        BaseResponse response = reviewService.getReviewsByEmail(email);
        logger.info(requestURI + " Endpoint was used");
        if(response.getStatusCode() == HttpStatus.OK.value()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @Operation(method = "GET", summary = "Retrieving all reviews", responses = {
            @ApiResponse(responseCode = "200",description = "Reviews",
            content = @Content(schema = @Schema(implementation = ReviewListDto.class))),
            @ApiResponse(responseCode = "400", description = "Failed to retrieve book")
    })
    ResponseEntity<?> getAllReviews(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        BaseResponse response = reviewService.getAllReviews();
        logger.info(requestURI + " Endpoint was used");
        if(response.getStatusCode() == HttpStatus.OK.value()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
