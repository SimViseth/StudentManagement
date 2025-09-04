package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.dto.request.CourseRequest;
import com.example.studentmanagement.model.dto.response.CourseResponse;
import com.example.studentmanagement.service.postgres.CourseService;
import com.example.studentmanagement.utils.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping("api/v1/course")
@Slf4j
public class CourseController {
    private final CourseService courseService;
    private final ObjectMapper objectMapper;

    @PostMapping
    @Operation(summary = "Create courses")
    public ResponseEntity<ApiResponse<CourseResponse>> createCourse(@Valid @RequestBody CourseRequest courseRequest) {

            CourseResponse payloadResponse = courseService.createCourse(courseRequest);

            ApiResponse<CourseResponse> response = ApiResponse.<CourseResponse>builder()
                    .status(HttpStatus.CREATED)
                    .statusCode(HttpStatus.CREATED.value())
                    .message("Course is created successfully")
                    .payload(payloadResponse)
                    .time(LocalDateTime.now())
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{courseId}")
    @Operation(summary = "Update courses")
    public ResponseEntity<ApiResponse<CourseResponse>> updateCourse(@Valid @PathVariable Integer courseId, @RequestBody CourseRequest courseRequest, HttpServletRequest request) {

        CourseResponse payloadResponse = courseService.updateCourse(courseId, courseRequest);

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
    public ResponseEntity<ApiResponse<String>> deleteCourse(@PathVariable Integer courseId, HttpServletRequest request) {

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
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getAllCourses(HttpServletRequest request) {

        List<CourseResponse> payloadResponse = courseService.getAllCourses();

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
    public ResponseEntity<ApiResponse<CourseResponse>> getCourseById(@Valid @PathVariable Integer courseId, HttpServletRequest request) {

        CourseResponse payloadResponse = courseService.getCourseById(courseId);

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
