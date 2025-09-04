package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.dto.request.ScoreRequest;
import com.example.studentmanagement.model.dto.response.ScoreResponse;
import com.example.studentmanagement.service.oracle.ScoreService;
import com.example.studentmanagement.utils.ApiResponse;
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

@RestController
@AllArgsConstructor
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
@Slf4j
@RequestMapping("api/v1/score")
public class ScoreController {

    private final ScoreService scoreService;

    @PostMapping
    public ResponseEntity<ApiResponse<ScoreResponse>> addScore(@Valid @RequestBody ScoreRequest scoreRequest, HttpServletRequest request) {

        ScoreResponse payloadResponse = scoreService.addScore(scoreRequest);

        ApiResponse<ScoreResponse> response = ApiResponse.<ScoreResponse>builder()
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .message("Score is added successfully")
                .payload(payloadResponse)
                .time(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{scoreId}")
    public ResponseEntity<ApiResponse<ScoreResponse>> updateScore(@Valid @PathVariable Integer scoreId, @RequestBody ScoreRequest scoreRequest, HttpServletRequest request) {

        ScoreResponse payloadResponse = scoreService.updateScore(scoreId, scoreRequest);

        ApiResponse<ScoreResponse> response = ApiResponse.<ScoreResponse>builder()
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .message("Score is updated successfully")
                .payload(payloadResponse)
                .time(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{scoreId}")
    public ResponseEntity<ApiResponse<String>> deleteScore(@Valid @PathVariable Integer scoreId, HttpServletRequest request) {

        scoreService.deleteScore(scoreId);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Score is deleted successfully")
                .time(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ScoreResponse>>> getAllScores(HttpServletRequest request) {

        List<ScoreResponse> payloadResponse = scoreService.getAllScores();

        ApiResponse<List<ScoreResponse>> response = ApiResponse.<List<ScoreResponse>>builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(payloadResponse)
                .message("Get all scores successfully")
                .time(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/{scoreId}")
    public ResponseEntity<ApiResponse<ScoreResponse>> getScoreById(@Valid @PathVariable Integer scoreId, HttpServletRequest request) {

        ScoreResponse payloadResponse = scoreService.getScoreById(scoreId);

        ApiResponse<ScoreResponse> response = ApiResponse.<ScoreResponse>builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .payload(payloadResponse)
                .message("Get score by id successfully")
                .time(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
