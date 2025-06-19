//package com.example.studentmanagement.controller;
//
//import com.example.studentmanagement.model.entity.mysql.Log;
//import com.example.studentmanagement.model.entity.mysql.User;
//import com.example.studentmanagement.repository.mysql.LogRepository;
//import com.example.studentmanagement.repository.mysql.UserRepository;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.LocalDateTime;
//
//@RestController
//@RequestMapping("/api/test")
//public class TestController {
//
//    private final UserRepository userRepository;
//    private final LogRepository logRepository;
//
//    public TestController(UserRepository userRepository, LogRepository logRepository) {
//        this.userRepository = userRepository;
//        this.logRepository = logRepository;
//    }
//
//    @PostMapping("/users")
//    public ResponseEntity<?> createUser(@RequestBody User user) {
//        return ResponseEntity.ok(userRepository.save(user));
//    }
//
//    @PostMapping("/logs")
//    public ResponseEntity<?> createLog(@RequestBody Log log) {
//        return ResponseEntity.ok(logRepository.save(log));
//    }
//}
