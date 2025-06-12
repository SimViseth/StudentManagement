package com.example.studentmanagement.repository;

import com.example.studentmanagement.model.entity.User;
import com.example.studentmanagement.model.entity.UserCourse;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCourseRepository extends JpaRepository<UserCourse, Integer> {
    @Transactional
    void deleteAllByUser(User user);
}
