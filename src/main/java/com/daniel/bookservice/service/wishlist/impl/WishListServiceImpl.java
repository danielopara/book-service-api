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

import java.util.*;

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
    @Transactional
    public BaseResponse wishABook(Long bookId, String email) {
        Optional<Book> book_id = bookRepository.findById(bookId);
        Optional<User> user_email = userRepository.findByEmail(email);
        if(user_email.isPresent() && book_id.isPresent()){
            Book book = book_id.get();

            Optional<WishList> byEmail = wishListRepository.findByEmail(email);
            if( byEmail.isPresent()){
                WishList wishList = byEmail.get();
                if(wishList.getBook().contains(book)){
                    return new BaseResponse(
                            HttpServletResponse.SC_OK,
                            "Exists already",
                            null,
                            null
                    );
                } else {

                    wishList.getBook().add(book);
                    wishListRepository.save(wishList);
                    return new BaseResponse(
                            HttpServletResponse.SC_OK,
                            "Book added to wishlist",
                            wishList,
                            null
                    );
                }
            } else {
                List<Book> books = new ArrayList<>();
                books.add(book);
                WishList newWishList = new WishList();
                newWishList.setBook(books);
                newWishList.setEmail(email);
                wishListRepository.save(newWishList);
                return new BaseResponse(
                        HttpServletResponse.SC_OK,
                        "Wishlist created and book added",
                        newWishList,
                        null
                );
            }
        }
        return new BaseResponse(
                HttpServletResponse.SC_BAD_REQUEST,
                "User or book not found",
                null,
                null
        );
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
