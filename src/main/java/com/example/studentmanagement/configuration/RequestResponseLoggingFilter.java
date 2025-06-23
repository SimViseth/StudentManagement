package com.example.studentmanagement.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {
    private static final Logger requestLogger = LoggerFactory.getLogger("REQUEST_LOGGER");
    private static final Logger responseLogger = LoggerFactory.getLogger("RESPONSE_LOGGER");
    private static final Logger exceptionLogger = LoggerFactory.getLogger("EXCEPTION_LOGGER");

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        String method = request.getMethod();
        String uri = request.getRequestURI();

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } catch (Exception ex) {
            String requestBody = extractRequestBody(wrappedRequest);
            logRequest(method, uri, requestBody, false);
            exceptionLogger.error("Exception occurred: {}", ex.getMessage());
            throw ex;
        }

        int status = wrappedResponse.getStatus();
        String responseBody = new String(wrappedResponse.getContentAsByteArray(), StandardCharsets.UTF_8);

        String requestBody = extractRequestBody(wrappedRequest);

        if (status < 400) {
            logRequest(method, uri, requestBody, true);
            logResponse(responseBody, false);
        } else {
            logRequest(method, uri, requestBody, false);
            logResponse(responseBody, true);
        }

        wrappedResponse.copyBodyToResponse();
    }

    private String extractRequestBody(ContentCachingRequestWrapper requestWrapper) {
        String method = requestWrapper.getMethod();
        if ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method) || "PATCH".equalsIgnoreCase(method)) {
            byte[] buf = requestWrapper.getContentAsByteArray();
            if (buf.length > 0) {
                try {
                    Object object = objectMapper.readValue(buf, Object.class);
                    return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
                } catch (Exception e) {
                    return new String(buf, StandardCharsets.UTF_8);
                }
            }
        }
        return "";
    }

    private void logRequest(String method, String uri, String requestBody, boolean isSuccess) {
        if (!requestBody.isEmpty()) {
            requestLogger.info("Request {} - {} {}\n{}",
                    isSuccess ? "successfully" : "fail", method, uri, requestBody);
        } else {
            requestLogger.info("Request {} - {} {}",
                    isSuccess ? "successfully" : "fail", method, uri);
        }
    }

    private void logResponse(String responseBody, boolean isError) {
        try {
            Object json = objectMapper.readValue(responseBody, Object.class);
            String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            if (isError) {
                exceptionLogger.error("\n{}", prettyJson);
            } else {
                responseLogger.info("\n{}", prettyJson);
            }
        } catch (Exception ex) {
            if (isError) {
                exceptionLogger.error("Non JSON error response: {}", responseBody);
            } else {
                responseLogger.info(responseBody);
            }
        }
    }
}