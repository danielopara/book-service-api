package com.daniel.bookservice.controller.user;

import com.daniel.bookservice.dto.LoginDto;
import com.daniel.bookservice.dto.RegisterDto;
import com.daniel.bookservice.response.BaseResponse;
import com.daniel.bookservice.service.user.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@CrossOrigin(origins = "*")
@Tag(name = "User API", description = "Resource for users")
public class UserController {
    private final UserServiceImpl userService;
    Logger logger = LoggerFactory.getLogger(UserController.class.getName());

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(method = "POST", summary = "Registers a user", responses = {
            @ApiResponse(responseCode = "200", description = "User registered",
                    content = @Content(schema = @Schema(implementation = RegisterDto.class),
                            examples = @ExampleObject(value = "{\"status\":\"200\"," +
                                    "\"description\":\"User created\"," +
                                    "\"data\":{" +
                                    "\"firstName\":\"John\"," +
                                    "\"lastName\":\"Doe\"," +
                                    "\"email\":\"john.doe@example.com\"," +
                                    "\"phoneNumber\":\"123-456-7890\"}}"))),
            @ApiResponse(responseCode = "400", description = "Failed to register a user")
    })

    ResponseEntity<?> register(@RequestBody RegisterDto registerDto, HttpServletRequest request){
        String requestURI = request.getRequestURI();
        logger.info(requestURI + " Endpoint was used");
        BaseResponse response = userService.userSignUp(registerDto);
        if(response.getStatusCode() == HttpStatus.OK.value()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    @Operation(method = "POST", summary = "Login", responses = {
            @ApiResponse(responseCode = "200", description = "Logged in successful",
                    content = @Content(schema = @Schema(implementation = LoginDto.class),
                            examples = @ExampleObject(value = "{\"statusCode\":\"200\"," +
                                    "\"description\":\"Login Successful\"," +
                                    "\"data\":\"eyJyb2xlIjoiQURNSU4iLCJzdWIiOiJvYmlAZ21haWwuY29tIiwiaWF0IjoxNzEzNjE0MDAzLCJleHAiOjE3MTM3MDA0MDN9\"}"))),
            @ApiResponse(responseCode = "400", description = "Failed to login")
    })
    ResponseEntity<?> login(@RequestBody LoginDto loginDto, HttpServletRequest request){
        String requestURI = request.getRequestURI();
        logger.info(requestURI + " Endpoint was used");
        BaseResponse response = userService.login(loginDto);
        if(response.getStatusCode() == HttpStatus.OK.value()){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
