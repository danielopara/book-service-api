package com.daniel.bookservice.service.review.impl;

import com.daniel.bookservice.dto.ReviewDto;
import com.daniel.bookservice.model.Book;
import com.daniel.bookservice.model.Review;
import com.daniel.bookservice.repository.BookRepository;
import com.daniel.bookservice.repository.ReviewRepository;
import com.daniel.bookservice.response.BaseResponse;
import com.daniel.bookservice.service.review.ReviewService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
            Book book = bookId.get();
            Review review = Review.builder()
                    .book(book)
                    .email(reviewDto.getEmail())
                    .review(reviewDto.getReview())
                    .rating(reviewDto.getRating())
                    .build();
            reviewRepository.save(review);
            Double averageRatingsById = bookRepository.findAverageRatingsById(reviewDto.getBookId());
            book.setRatings(averageRatingsById);
            bookRepository.save(book);

            return new BaseResponse(HttpServletResponse.SC_OK, "Review added", review, null);
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
