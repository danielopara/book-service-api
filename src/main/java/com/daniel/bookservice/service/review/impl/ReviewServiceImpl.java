package com.daniel.bookservice.service.review.impl;

import com.daniel.bookservice.dto.ReviewDto;
import com.daniel.bookservice.dto.ReviewListDto;
import com.daniel.bookservice.model.Book;
import com.daniel.bookservice.model.Review;
import com.daniel.bookservice.repository.BookRepository;
import com.daniel.bookservice.repository.ReviewRepository;
import com.daniel.bookservice.response.BaseResponse;
import com.daniel.bookservice.service.review.ReviewService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(BookRepository bookRepository, ReviewRepository reviewRepository) {
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    @Transactional
    public BaseResponse addReview(ReviewDto reviewDto) {
        try{
            Optional<Book> bookId = bookRepository.findById(reviewDto.getBookId());
            if(bookId.isEmpty()){
                return new BaseResponse(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Book not found",
                        reviewDto.getBookId(),
                        "Book not found"
                );
            }

            if (reviewDto.getRating() < 1 || reviewDto.getRating() > 5) {
                return new BaseResponse(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid rating",
                        reviewDto.getRating(),
                        "Rating cannot be less than 1 or greater than 5"
                );
            }
            Book book = bookId.get();
            Review review = Review.builder()
                    .book(book)
                    .email(reviewDto.getEmail())
                    .review(reviewDto.getReview())
                    .rating(reviewDto.getRating())
                    .build();
            reviewRepository.save(review);

            calculateAndUpdateReview(book);
            return new BaseResponse(HttpServletResponse.SC_OK, "Review added", review, null);
        } catch (Exception e){
            return new BaseResponse(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Error",
                    e.getMessage(),
                    e.getCause().toString()
            );
        }
    }

    @Override
    public BaseResponse getReviewsByEmail(String email) {
        try{
//            ReviewDto dto = new ReviewDto();
            List<Review> userEmail = reviewRepository.findByEmail(email);
            if(userEmail.isEmpty()){
                return new BaseResponse(
                        HttpServletResponse.SC_NOT_FOUND,
                        "No reviews for this user",
                        null,
                        null
                );
            }
            Stream<ReviewListDto> reviewListDtoStream = userEmail.stream()
                    .map(review -> new ReviewListDto(
                            review.getEmail(),
                            review.getReview(),
                            review.getRating(),
                            review.getBook().getTitle(),
                            review.getCreatedAT()
                    ));
            return new BaseResponse(
                    HttpServletResponse.SC_OK,
                    "Reviews for user: " + email,
                    reviewListDtoStream,
                    null
            );
        } catch (Exception e){
            return new BaseResponse(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Error",
                    null,
                    e.getMessage()
            );
        }
    }

    @Override
    public BaseResponse getAllReviews() {
        try{
            List<Review> reviews = reviewRepository.findAll();
            if(reviews.isEmpty()){
                return new BaseResponse(
                        HttpServletResponse.SC_NOT_FOUND,
                        "No reviews",
                        null,
                        null
                );
            }
            Stream<ReviewListDto> reviewListDtoStream = reviews.stream()
                    .map(review -> new ReviewListDto(
                            review.getEmail(),
                            review.getReview(),
                            review.getRating(),
                            review.getBook().getTitle(),
                            review.getCreatedAT()
                    ));
            return new BaseResponse(
                    HttpServletResponse.SC_OK,
                    "Reviews",
                    reviewListDtoStream,
                    null
            );

        }catch (Exception e){
            return new BaseResponse(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Error",
                    null,
                    e.getMessage()
            );
        }
    }

    private void calculateAndUpdateReview(Book book){
        Double averageRatingsById = bookRepository.findAverageRatingsById(book.getId());
        BigDecimal averageRatings = BigDecimal.valueOf(averageRatingsById).setScale(2, RoundingMode.HALF_UP);
        book.setRatings(averageRatings.doubleValue());
        bookRepository.save(book);
    }
}
