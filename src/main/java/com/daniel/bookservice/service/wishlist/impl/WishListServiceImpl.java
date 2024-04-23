package com.daniel.bookservice.service.wishlist.impl;

import com.daniel.bookservice.dto.WishListDto;
import com.daniel.bookservice.model.Book;
import com.daniel.bookservice.model.User;
import com.daniel.bookservice.model.WishList;
import com.daniel.bookservice.repository.BookRepository;
import com.daniel.bookservice.repository.UserRepository;
import com.daniel.bookservice.repository.WishListRepository;
import com.daniel.bookservice.response.BaseResponse;
import com.daniel.bookservice.service.wishlist.WishListService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@Service
public class WishListServiceImpl implements WishListService {
    private final UserRepository userRepository;
    private final WishListRepository wishListRepository;
    private final BookRepository bookRepository;

    public WishListServiceImpl(UserRepository userRepository, WishListRepository wishListRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.wishListRepository = wishListRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public BaseResponse wishABook(Long bookId, String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        Optional<Book> byId = bookRepository.findById(bookId);
        if(byEmail.isPresent() && byId.isPresent()){
            User user = byEmail.get();
            Book book = byId.get();

            // Assuming you have a method to get the wishlist by user
            Optional<WishList> wishListOptional = wishListRepository.findByEmail(email);
            WishList wishList;

            if (wishListOptional.isPresent()) {
                wishList = wishListOptional.get();
                // Add the book to the existing wishlist
                wishList.getBook().add(book);
            } else {
                // Create a new wishlist if it doesn't exist
                Set<Book> books = new HashSet<>();
                books.add(book);
                wishList = WishList.builder()
                        .email(email)
                        .book(books)
                        .build();
            }

            // Save the wishlist
            wishListRepository.save(wishList);
            return new BaseResponse(HttpStatus.OK.value(),
                    "Added to wishlist",
                    wishList,
                    null);
        }
        return new BaseResponse(HttpStatus.BAD_REQUEST.value(),
                "Error",
                null,
                null);
    }

    @Override
    @Transactional
    public BaseResponse getWishListByEmail(String email) {
        try{
            Optional<WishList> byEmail = wishListRepository.findByEmail(email);
            if(byEmail.isEmpty()){
                return new BaseResponse(
                        HttpStatus.OK.value(),
                        "No item",
                        null,
                        null
                );
            }
            byEmail.get().getBook().size();
            WishListDto dto = WishListDto.builder()
                    .book(byEmail.get().getBook())
                    .build();

            return new BaseResponse(
                    HttpStatus.OK.value(),
                    "SUCCESS",
                    dto,
                    null
            );
        } catch (Exception e){
         return new BaseResponse(
                 HttpServletResponse.SC_BAD_REQUEST,
                 "ERROR",
                 null,
                 null
         );
        }
    }


    private boolean checkForUser(String email, Long bookId){
        Optional<User> byEmail = userRepository.findByEmail(email);
        Optional<Book> byId = bookRepository.findById(bookId);
        return byEmail.isPresent() && byId.isPresent();
    }
}
