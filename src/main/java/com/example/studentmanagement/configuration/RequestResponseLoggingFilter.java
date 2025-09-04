package com.example.studentmanagement.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

            ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
            ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

            filterChain.doFilter(wrappedRequest, wrappedResponse);

            String requestBody = new String(wrappedRequest.getContentAsByteArray(), request.getCharacterEncoding());
            String responseBody = new String(wrappedResponse.getContentAsByteArray(), response.getCharacterEncoding());

            HttpStatus status = HttpStatus.valueOf(response.getStatus());
            String inlineBody = inlineJson(requestBody);

            if (status.isError()) {
                log.error("Request fail: {} {} {}", request.getMethod(), request.getRequestURI(), inlineBody);
                log.error("Error: {}", responseBody);
            } else {
                log.info("Request: {} {} {}", request.getMethod(), request.getRequestURI(), inlineBody);
                log.info("Response: {}", responseBody);
            }
            wrappedResponse.copyBodyToResponse();
    }

    private String inlineJson(String json) throws JsonProcessingException {
        if (json == null || json.trim().isEmpty()) {
            return "";
        }
        else {
            ObjectMapper objectMapper = new ObjectMapper();
            Object jsonObj = objectMapper.readValue(json, Object.class);
            return objectMapper.writeValueAsString(jsonObj);
        }
    }
}