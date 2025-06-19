package com.example.studentmanagement.service.mysql;

import com.example.studentmanagement.model.dto.request.AuthRequest;
import com.example.studentmanagement.model.dto.request.UserRequest;
import com.example.studentmanagement.model.dto.response.AuthResponse;
import com.example.studentmanagement.model.dto.response.UserResponse;

public interface AuthService {
    UserResponse createUser(UserRequest userRequest);
    AuthResponse login(AuthRequest authRequest);
    UserResponse updateUser(Integer userId, UserRequest userRequest);
}
