package com.example.studentmanagement.repository.postgres;

import com.example.studentmanagement.model.entity.postgres.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, Integer> {
}
