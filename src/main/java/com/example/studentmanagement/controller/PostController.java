package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.dto.request.PostRequest;
import com.example.studentmanagement.model.dto.response.PostResponse;
import com.example.studentmanagement.utils.ApiResponse;
import com.example.studentmanagement.client.ApiService;
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
@RestController
@RequestMapping("api/v1/posts")
@Slf4j
public class PostController {

    private final ApiService apiService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponse>>> getAllPosts() {
        List<PostResponse> payloadResponse = apiService.getPosts();
        log.info("Get all posts: {}", payloadResponse);

        ApiResponse<List<PostResponse>> response = ApiResponse.<List<PostResponse>>builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(payloadResponse)
                .message("Get all posts successfully")
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> getPostById(@Valid @PathVariable Long id) {
        PostResponse payloadResponse = apiService.getPostById(id);
        log.info("Get post by id: {}", payloadResponse);

        ApiResponse<PostResponse> response = ApiResponse.<PostResponse>builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(payloadResponse)
                .message("Get posts by id successfully")
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PostResponse>> createPost(@RequestBody PostRequest postRequest) {
        PostResponse payloadResponse = apiService.createPost(postRequest);

        ApiResponse<PostResponse> response = ApiResponse.<PostResponse>builder()
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .payload(payloadResponse)
                .message("Add post successfully")
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(@Valid @PathVariable Long id, @RequestBody PostRequest postRequest) {
        PostResponse payloadResponse = apiService.updatePost(id, postRequest);

        ApiResponse<PostResponse> response = ApiResponse.<PostResponse>builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(payloadResponse)
                .message("Post updated successfully")
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deletePost(@Valid @PathVariable Long id) {
        apiService.deletePost(id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Post updated successfully")
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
