package com.example.studentmanagement.service;

import com.example.studentmanagement.model.dto.request.UserRequest;
import com.example.studentmanagement.model.dto.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
    UserResponse loadUserByUserId() throws UsernameNotFoundException;
    List<UserResponse> getAllUser();

    void deleteUser(Integer userId);
}
