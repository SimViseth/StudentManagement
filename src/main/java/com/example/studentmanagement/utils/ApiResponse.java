package com.example.studentmanagement.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ApiResponse <T> {
    private String message;
    private HttpStatus status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer statusCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime time;
}
