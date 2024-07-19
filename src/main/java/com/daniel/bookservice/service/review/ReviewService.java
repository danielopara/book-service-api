package com.daniel.bookservice.service.review;

import com.daniel.bookservice.dto.ReviewDto;
import com.daniel.bookservice.response.BaseResponse;
import com.daniel.bookservice.service.review.reviewInterface.ReviewCreationService;

public interface ReviewService extends ReviewCreationService {

    BaseResponse getReviewsByEmail(String email);
    BaseResponse getAllReviews();
}
