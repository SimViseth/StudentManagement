package com.example.studentmanagement.model.entity.postgres;

import com.example.studentmanagement.model.entity.mysql.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "userCourse")
public class UserCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId; // from User in MySQL

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
