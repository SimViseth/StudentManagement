package com.example.studentmanagement.model.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostResponse {
    private Integer userId;
    private Integer id;
    private String title;
    private String body;
}
