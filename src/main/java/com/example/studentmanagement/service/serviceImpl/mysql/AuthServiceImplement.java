package com.example.studentmanagement.service.serviceImpl.mysql;

import com.example.studentmanagement.exception.BadRequestException;
import com.example.studentmanagement.exception.ConflictException;
import com.example.studentmanagement.exception.NotFoundException;
import com.example.studentmanagement.jwt.JwtService;
import com.example.studentmanagement.model.dto.request.AuthRequest;
import com.example.studentmanagement.model.dto.request.UserRequest;
import com.example.studentmanagement.model.dto.response.AuthResponse;
import com.example.studentmanagement.model.dto.response.UserResponse;
import com.example.studentmanagement.model.entity.postgres.Course;
import com.example.studentmanagement.model.entity.mysql.User;
import com.example.studentmanagement.model.entity.postgres.UserCourse;
import com.example.studentmanagement.repository.postgres.CourseRepository;
import com.example.studentmanagement.repository.postgres.UserCourseRepository;
import com.example.studentmanagement.repository.mysql.UserRepository;
import com.example.studentmanagement.service.mysql.AuthService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CourseRepository courseRepository;
    private final UserCourseRepository userCourseRepository;
    @Override
    public UserResponse createUser(UserRequest userRequest) {
        checkEmailAndPassword(userRequest);

        User user = modelMapper.map(userRequest, User.class);

        User saveUser = userRepository.save(user);

        if (userRequest.getCourseIds() != null) {

            for (Integer courseId : userRequest.getCourseIds()) {

                Course course = courseRepository.findById(courseId).orElseThrow(() -> new NotFoundException("Course not found"));

                UserCourse userCourse = new UserCourse();
                userCourse.setUserId(saveUser.getUserId());
                userCourse.setCourse(course);

                userCourseRepository.save(userCourse);
            }
        }
        return modelMapper.map(saveUser, UserResponse.class);
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        authenticate(authRequest.getEmail(), authRequest.getPassword());
        User user = userRepository.findByEmail(authRequest.getEmail());
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

    @Override
    public UserResponse updateUser(Integer userId, UserRequest userRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));


        user.setUsername(userRequest.getUserName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setAddress(userRequest.getAddress());
        user.setPhoneNumber(userRequest.getPhoneNumber());

        userCourseRepository.deleteAllByUserId(user.getUserId());

        for (Integer courseId : userRequest.getCourseIds()) {

            Course course = courseRepository.findById(courseId).orElseThrow(() -> new NotFoundException("Course not found"));

            UserCourse userCourse = new UserCourse();
            userCourse.setUserId(userId);
            userCourse.setCourse(course);
            userCourseRepository.save(userCourse);
        }

        User saveUser = userRepository.save(user);

        return modelMapper.map(saveUser, UserResponse.class);
    }

    private void checkEmailAndPassword(UserRequest userRequest) {
        User user = userRepository.findByEmail(userRequest.getEmail());

        if(!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            throw new BadRequestException("Confirm password does not match");
        }
        if (user != null) {
            throw new ConflictException("Email already registered");
        }
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
    }

    private void authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new NotFoundException("Invalid Email Address");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new NotFoundException("Password do not match");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
