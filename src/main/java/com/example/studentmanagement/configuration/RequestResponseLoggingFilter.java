//package com.example.studentmanagement.configuration;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.web.util.ContentCachingResponseWrapper;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//
//@Component
//public class RequestResponseLoggingFilter extends OncePerRequestFilter {
//    private static final Logger requestLogger = LoggerFactory.getLogger("REQUEST_LOGGER");
//    private static final Logger responseLogger = LoggerFactory.getLogger("RESPONSE_LOGGER");
//    private static final Logger exceptionLogger = LoggerFactory.getLogger("EXCEPTION_LOGGER");
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        try {
//            // Log request details
//            requestLogger.info("{} {} from {}", request.getMethod(), request.getRequestURI(), request.getRemoteAddr());
//
//            // Wrap response to capture content
//            ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
//            filterChain.doFilter(request, wrappedResponse);
//
//            // After the response is ready, log it
//            String responseBody = new String(wrappedResponse.getContentAsByteArray(), StandardCharsets.UTF_8);
//
//            responseLogger.info(responseBody); // If response is your ApiResponse, this will work as desired
//
//            wrappedResponse.copyBodyToResponse(); // Important to restore the response body
//
//        } catch (Exception ex) {
//            exceptionLogger.error("Exception occurred: {}", ex.getMessage(), ex);
//            throw ex;
//        }
//    }
//}
