package com.daniel.bookservice.service.wishlist;

import com.daniel.bookservice.model.WishList;
import com.daniel.bookservice.response.BaseResponse;

import java.util.Optional;

public interface WishListService {
    BaseResponse wishABook(Long bookId, String email);
    BaseResponse getWishListByEmail(String email);
}
