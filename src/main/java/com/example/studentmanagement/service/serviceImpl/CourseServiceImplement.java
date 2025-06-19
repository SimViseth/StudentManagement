package com.example.studentmanagement.service.serviceImpl;

import com.example.studentmanagement.exception.NotFoundException;
import com.example.studentmanagement.model.dto.request.CourseRequest;
import com.example.studentmanagement.model.dto.response.CourseResponse;
import com.example.studentmanagement.model.entity.postgres.Course;
import com.example.studentmanagement.repository.postgres.CourseRepository;
import com.example.studentmanagement.service.CourseService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseServiceImplement implements CourseService {

    private final ModelMapper modelMapper;
    private final CourseRepository courseRepository;
    @Override
    public CourseResponse createCourse(CourseRequest courseRequest) {
        Course course = modelMapper.map(courseRequest, Course.class);

        Course saveCourse = courseRepository.save(course);

        return modelMapper.map(saveCourse, CourseResponse.class);
    }

    @Override
    public CourseResponse updateCourse(Integer courseId, CourseRequest courseRequest) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found"));

        course.setCourseName(courseRequest.getCourseName());
        course.setDescription(courseRequest.getDescription());

        Course updateCourse = courseRepository.save(course);

        return modelMapper.map(updateCourse, CourseResponse.class);
    }

    @Override
    public void deleteCourse(Integer courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new NotFoundException("Course not found");
        }
        courseRepository.deleteById(courseId);
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        List<Course> courseList = courseRepository.findAll();

        return courseList.stream().map((course) ->
                modelMapper.map(course, CourseResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public CourseResponse getCourseById(Integer courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new NotFoundException("Course not found"));

        return modelMapper.map(course, CourseResponse.class);
    }
}
