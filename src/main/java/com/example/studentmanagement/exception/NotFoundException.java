package com.example.studentmanagement.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) { // constructor to pass custom message like use in serviceImp
        super(message); // call runtimeException constructor and store message
    }
}

// Extend RuntimeException for uncheck exception, if don't extend it, we need to handle it by using throw

// public User getUserById(Long id) throws NotFoundException {
//        return userRepository.findById(id)
//        .orElseThrow(() -> new NotFoundException("User not found"));
//        }
