package com.example.studentmanagement.utils;

import com.example.studentmanagement.model.entity.mysql.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class GetCurrentUser {
    public static Integer userId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUserId();
    }
}
