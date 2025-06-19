package com.example.studentmanagement.model.entity.postgres;

import com.example.studentmanagement.model.entity.oracle.Score;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;
    private String courseName;
    private String description;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<UserCourse> userCourses;

//    @OneToMany(mappedBy = "course")
//    private List<Score> score;
}
