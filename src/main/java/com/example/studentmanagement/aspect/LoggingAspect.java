package com.example.studentmanagement.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    public void log() {
        System.out.println("Aspect log called");
    }
}
