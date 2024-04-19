package com.daniel.bookservice.service.user.impl;

import com.daniel.bookservice.config.jwt.JwtService;
import com.daniel.bookservice.dto.LoginDto;
import com.daniel.bookservice.dto.RegisterDto;
import com.daniel.bookservice.model.Auth;
import com.daniel.bookservice.model.User;
import com.daniel.bookservice.model.enums.Roles;
import com.daniel.bookservice.repository.AuthRepository;
import com.daniel.bookservice.repository.UserRepository;
import com.daniel.bookservice.response.BaseResponse;
import com.daniel.bookservice.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthRepository authRepository;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class.getName());

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, AuthRepository authRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.authRepository = authRepository;
    }

    @Override
    public BaseResponse userSignUp(RegisterDto registerDto) {
        BaseResponse response = new BaseResponse();

        Boolean doesEmailExist = userRepository.existsByEmail(registerDto.getEmail());
        if(doesEmailExist){
            response.setDescription("Email exists");
            response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());

            return response;
        }
        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPhoneNumber(registerDto.getPhoneNumber());
        user.setRole(Roles.USER);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Map<String, Object> registerDtoList = new HashMap<>();
        registerDtoList.put("firstName", registerDto.getFirstName());
        registerDtoList.put("lastName", registerDto.getLastName());
        registerDtoList.put("email", registerDto.getEmail());
        registerDtoList.put("phoneNumber", registerDto.getPhoneNumber());

        response.setStatusCode(HttpStatus.OK.value());
        response.setDescription("User created");
        response.setData(registerDtoList);
        userRepository.save(user);
        logger.info(String.valueOf(response));
        return response;
    }

    @Override
    public BaseResponse adminSignUp(RegisterDto registerDto) {
        BaseResponse response = new BaseResponse();

        Boolean doesEmailExist = userRepository.existsByEmail(registerDto.getEmail());
        if(doesEmailExist){
            response.setDescription("Email exists");
            response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());

            return response;
        }
        long adminCount = userRepository.countByRole(Roles.ADMIN);
        if (adminCount >= 2) {
            response.setDescription("Cannot create more than 2 admins");
            response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());

            return response;
        }
        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPhoneNumber(registerDto.getPhoneNumber());
        user.setRole(Roles.ADMIN);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Map<String, Object> registerDtoList = new HashMap<>();
        registerDtoList.put("firstName", registerDto.getFirstName());
        registerDtoList.put("lastName", registerDto.getLastName());
        registerDtoList.put("email", registerDto.getEmail());
        registerDtoList.put("phoneNumber", registerDto.getPhoneNumber());

        response.setStatusCode(HttpStatus.OK.value());
        response.setDescription("User created");
        response.setData(registerDtoList);
        userRepository.save(user);
        logger.info(String.valueOf(response));
        return response;
    }

    @Override
    public BaseResponse login(LoginDto loginDto) {
        try {
            BaseResponse response = new BaseResponse();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(),
                    loginDto.getPassword()
            ));
            var user = userRepository.findByEmail(loginDto.getEmail());
            if (user.isPresent()) {
                Auth auth = new Auth();
                String token = jwtService.generateToken(user.get());
                Date expiration = jwtService.extractExpiration(token);
                Date creation = jwtService.extractTokenCreation(token);

                auth.setEmail(user.get().getEmail());
                auth.setCreation(creation);
                auth.setExpiry(expiration);
                auth.setToken(token);
                authRepository.save(auth);

                response.setStatusCode(HttpStatus.OK.value());
                response.setDescription("Login Successful");
                response.setData(token);
                logger.info(String.valueOf(response));
                return response;
            }
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setError("Username or password Invalid");
            response.setDescription("Provide the correct username or password");
            response.setData(loginDto);
            logger.error(String.valueOf(response));
            return response;
        }catch (Exception e){
            return new BaseResponse(HttpStatus.BAD_REQUEST.value(), "Authentication Failed", null, e.getMessage());
        }
    }
}
