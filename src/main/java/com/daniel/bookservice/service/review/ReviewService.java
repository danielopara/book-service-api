package com.daniel.bookservice.service.review;

import com.daniel.bookservice.dto.ReviewDto;
import com.daniel.bookservice.response.BaseResponse;

public interface ReviewService {
    BaseResponse addReview(ReviewDto reviewDto);
    BaseResponse getReviewsByEmail(String email);
    BaseResponse getAllReviews();
}
