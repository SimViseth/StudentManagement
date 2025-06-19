package com.example.studentmanagement.model.entity.oracle;

import com.example.studentmanagement.model.entity.mysql.User;
import com.example.studentmanagement.model.entity.postgres.Course;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "score")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scoreId;
    private String score;

    private Integer userId; // store only ID

    private Integer courseId;
}
