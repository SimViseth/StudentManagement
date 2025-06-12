package com.example.studentmanagement.service;

import com.example.studentmanagement.model.dto.request.ScoreRequest;
import com.example.studentmanagement.model.dto.response.ScoreResponse;

import java.util.List;

public interface ScoreService {
    ScoreResponse addScore(ScoreRequest scoreRequest);
    ScoreResponse updateScore(Integer scoreId, ScoreRequest scoreRequest);

    void deleteScore(Integer scoreId);

    List<ScoreResponse> getAllScores();

    ScoreResponse getScoreById(Integer scoreId);
}
