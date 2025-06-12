package com.example.studentmanagement.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScoreRequest {
    private String score;
    private Integer courseId;
    private Integer userId;
}
