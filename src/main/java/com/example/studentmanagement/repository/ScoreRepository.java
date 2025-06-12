package com.example.studentmanagement.repository;

import com.example.studentmanagement.model.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, Integer> {
}
