package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.dto.request.AuthRequest;
import com.example.studentmanagement.model.dto.request.UserRequest;
import com.example.studentmanagement.model.dto.response.AuthResponse;
import com.example.studentmanagement.model.dto.response.UserResponse;
import com.example.studentmanagement.service.AuthService;
import com.example.studentmanagement.utils.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
@CrossOrigin
@Slf4j
@RequestMapping("api/v1/auths")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody UserRequest userRequest, HttpServletRequest request) {

        log.info("Request: {} {}", request.getMethod(), request.getRequestURI());

        UserResponse payloadResponse = authService.createUser(userRequest);

        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .message("A new user is created successfully")
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .payload(payloadResponse)
                .time(LocalDateTime.now())
                .build();

        log.info("Response: {}", payloadResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody AuthRequest authRequest, HttpServletRequest request) {

        log.info("Request: {} {}", request.getMethod(), request.getRequestURI());

        AuthResponse payloadResponse = authService.login(authRequest);
        ApiResponse<AuthResponse> response = ApiResponse.<AuthResponse>builder()
                .message("You are login successfully")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(payloadResponse)
                .time(LocalDateTime.now())
                .build();

        log.info("Response: {}", payloadResponse);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
