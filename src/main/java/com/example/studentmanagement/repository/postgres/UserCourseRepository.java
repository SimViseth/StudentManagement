package com.example.studentmanagement.repository.postgres;

import com.example.studentmanagement.model.entity.postgres.User;
import com.example.studentmanagement.model.entity.postgres.UserCourse;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCourseRepository extends JpaRepository<UserCourse, Integer> {
    @Transactional
    void deleteAllByUser(User user);
}
