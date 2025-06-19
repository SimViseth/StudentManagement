package com.example.studentmanagement.repository.postgres;

import com.example.studentmanagement.model.entity.postgres.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
