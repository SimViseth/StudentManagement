package com.example.studentmanagement.repository.postgres;

import com.example.studentmanagement.model.entity.postgres.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}
