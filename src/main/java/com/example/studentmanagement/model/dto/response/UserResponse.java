package com.example.studentmanagement.model.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponse {
    private Integer userId;
    private String username;
    private String address;
    private String phoneNumber;
    private String email;
}
