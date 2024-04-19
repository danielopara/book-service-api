package com.daniel.bookservice.service.user;

import com.daniel.bookservice.dto.LoginDto;
import com.daniel.bookservice.dto.RegisterDto;
import com.daniel.bookservice.response.BaseResponse;

public interface UserService {
    BaseResponse signUp(RegisterDto registerDto);
    BaseResponse login(LoginDto loginDto);
}
