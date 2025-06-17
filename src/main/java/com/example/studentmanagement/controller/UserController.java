package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.dto.request.UserRequest;
import com.example.studentmanagement.model.dto.response.UserResponse;
import com.example.studentmanagement.service.AuthService;
import com.example.studentmanagement.service.UserService;
import com.example.studentmanagement.utils.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/v1/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUser(HttpServletRequest request) {

        log.info("Request: {} {}", request.getMethod(), request.getRequestURI());

        List<UserResponse> payloadResponse = userService.getAllUser();

        ApiResponse<List<UserResponse>> response = ApiResponse.<List<UserResponse>>builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(payloadResponse)
                .message("Get all user successfully")
                .time(LocalDateTime.now())
                .build();

        log.info("Response: {}", payloadResponse);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@Valid @PathVariable Integer userId, @RequestBody UserRequest userRequest, HttpServletRequest request) {

        log.info("Request: {} {}", request.getMethod(), request.getRequestURI());

        UserResponse payloadResponse = authService.updateUser(userId, userRequest);
        
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(payloadResponse)
                .message("User updated successfully")
                .time(LocalDateTime.now())
                .build();

        log.info("Response: {}", payloadResponse);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Integer userId, HttpServletRequest request) {

        log.info("Request: {} {}", request.getMethod(), request.getRequestURI());

        userService.deleteUser(userId);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("user is deleted successfully")
                .time(LocalDateTime.now())
                .build();

        log.info("Response: {}", response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
