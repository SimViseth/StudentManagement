package com.example.studentmanagement.service.postgres;

import com.example.studentmanagement.model.dto.request.CourseRequest;
import com.example.studentmanagement.model.dto.response.CourseResponse;

import java.util.List;

public interface CourseService {
    CourseResponse createCourse(CourseRequest courseRequest);

    CourseResponse updateCourse(Integer courseId, CourseRequest courseRequest);

    void deleteCourse(Integer courseId);

    List<CourseResponse> getAllCourses();

    CourseResponse getCourseById(Integer courseId);
}
