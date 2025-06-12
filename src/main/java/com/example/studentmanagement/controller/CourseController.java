package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.dto.request.CourseRequest;
import com.example.studentmanagement.model.dto.response.CourseResponse;
import com.example.studentmanagement.service.CourseService;
import com.example.studentmanagement.utils.ApiResponse;
import com.example.studentmanagement.client.ApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "*")
@RestController
@Slf4j
@RequestMapping("api/v1/course")

public class CourseController {
    private final CourseService courseService;
    private final ApiService apiService;

    @PostMapping
    @Operation(summary = "Create courses")
    public ResponseEntity<ApiResponse<CourseResponse>> createCourse(@Valid @RequestBody CourseRequest courseRequest) {
        CourseResponse payloadResponse = courseService.createCourse(courseRequest);

        log.info("New course is created: {}", payloadResponse);
        ApiResponse<CourseResponse> response = ApiResponse.<CourseResponse>builder()
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .message("Course is created successfully")
                .payload(payloadResponse)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{courseId}")
    @Operation(summary = "Update courses")
    public ResponseEntity<ApiResponse<CourseResponse>> updateCourse(@Valid @PathVariable Integer courseId, @RequestBody CourseRequest courseRequest) {
        CourseResponse payloadResponse = courseService.updateCourse(courseId, courseRequest);
        log.info("Course is updated: {}", payloadResponse);
        
        ApiResponse<CourseResponse> response = ApiResponse.<CourseResponse>builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Course is updated successfully")
                .payload(payloadResponse)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{courseId}")
    @Operation(summary = "Delete courses")
    public ResponseEntity<ApiResponse<String>> deleteCourse(@PathVariable Integer courseId) {
        courseService.deleteCourse(courseId);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Course is deleted successfully")
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getAllCourses() {
        List<CourseResponse> payloadResponse = courseService.getAllCourses();
        log.info("Get all courses: {}", payloadResponse);

        ApiResponse<List<CourseResponse>> response = ApiResponse.<List<CourseResponse>>builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(payloadResponse)
                .message("Get all courses successfully")
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/{courseId}")
    public ResponseEntity<ApiResponse<CourseResponse>> getCourseById(@Valid @PathVariable Integer courseId) {
        CourseResponse payloadResponse = courseService.getCourseById(courseId);
        log.info("Get course by id: {}", payloadResponse);

        ApiResponse<CourseResponse> response = ApiResponse.<CourseResponse>builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(payloadResponse)
                .message("Get course by id successfully")
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
