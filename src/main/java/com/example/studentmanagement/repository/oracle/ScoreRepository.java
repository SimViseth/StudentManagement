package com.example.studentmanagement.repository.oracle;

import com.example.studentmanagement.model.entity.oracle.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, Integer> {
}
