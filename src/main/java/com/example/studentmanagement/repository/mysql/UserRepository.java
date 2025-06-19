package com.example.studentmanagement.repository.mysql;

import com.example.studentmanagement.model.entity.mysql.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
