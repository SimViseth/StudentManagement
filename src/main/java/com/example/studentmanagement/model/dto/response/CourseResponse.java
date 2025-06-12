package com.example.studentmanagement.model.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourseResponse {
    private Integer courseId;
    private String courseName;
    private String description;
}
