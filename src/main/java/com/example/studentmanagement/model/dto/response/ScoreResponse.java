package com.example.studentmanagement.model.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ScoreResponse {
    private Integer scoreId;
    private String score;
    private String courseName;
    private Integer userId;
}
