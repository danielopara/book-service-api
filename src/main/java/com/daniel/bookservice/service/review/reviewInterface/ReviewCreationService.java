package com.daniel.bookservice.service.review.reviewInterface;

import com.daniel.bookservice.dto.ReviewDto;
import com.daniel.bookservice.response.BaseResponse;

public interface ReviewCreationService {
    BaseResponse addReview(ReviewDto reviewDto);
}
