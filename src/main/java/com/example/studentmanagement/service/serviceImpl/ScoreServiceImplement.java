package com.example.studentmanagement.service.serviceImpl;

import com.example.studentmanagement.exception.NotFoundException;
import com.example.studentmanagement.model.dto.request.ScoreRequest;
import com.example.studentmanagement.model.dto.response.ScoreResponse;
import com.example.studentmanagement.model.entity.Course;
import com.example.studentmanagement.model.entity.Score;
import com.example.studentmanagement.model.entity.User;
import com.example.studentmanagement.repository.CourseRepository;
import com.example.studentmanagement.repository.ScoreRepository;
import com.example.studentmanagement.repository.UserRepository;
import com.example.studentmanagement.service.ScoreService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScoreServiceImplement implements ScoreService {

    private final ScoreRepository scoreRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    @Override
    public ScoreResponse addScore(ScoreRequest scoreRequest) {

        User user = userRepository.findById(scoreRequest.getUserId()).orElseThrow(() -> new NotFoundException("User not found"));
        Course course = courseRepository.findById(scoreRequest.getCourseId()).orElseThrow(() -> new NotFoundException("Course not found"));

        Score score = new Score();
        score.setScore(scoreRequest.getScore());
        score.setUser(user);
        score.setCourse(course);

        Score saveScore = scoreRepository.save(score);

        ScoreResponse response = modelMapper.map(saveScore, ScoreResponse.class);
        response.setCourseName(saveScore.getCourse().getCourseName());
        response.setUserName(saveScore.getUser().getUsername());

        return response;

    }

    @Override
    public ScoreResponse updateScore(Integer scoreId, ScoreRequest scoreRequest) {
        User user = userRepository.findById(scoreRequest.getUserId()).orElseThrow(() -> new NotFoundException("User not found"));
        Course course = courseRepository.findById(scoreRequest.getCourseId()).orElseThrow(() -> new NotFoundException("Course not found"));

        Score score = scoreRepository.findById(scoreId).orElseThrow(() -> new NotFoundException("Score not found"));
        score.setScore(scoreRequest.getScore());
        score.setCourse(course);
        score.setUser(user);

        Score updateScore = scoreRepository.save(score);

        return modelMapper.map(updateScore, ScoreResponse.class);
    }

    @Override
    public void deleteScore(Integer scoreId) {

        if (!scoreRepository.existsById(scoreId)) {
            throw new NotFoundException("Score with id " + scoreId + " does not exist");
        }

        scoreRepository.deleteById(scoreId);
    }

    @Override
    public List<ScoreResponse> getAllScores() {
        List<Score> scoreList = scoreRepository.findAll();

        return scoreList.stream()
                .map(score -> modelMapper.map(score, ScoreResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public ScoreResponse getScoreById(Integer scoreId) {
        Score score = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new NotFoundException("Score not found"));

        return modelMapper.map(score, ScoreResponse.class);
    }
}
