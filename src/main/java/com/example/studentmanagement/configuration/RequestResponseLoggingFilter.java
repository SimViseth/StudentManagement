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
            logRequestDetails(wrappedRequest);
            requestLogger.info("Request Fail: EXCEPTION at {} {}", method, uri);
            exceptionLogger.error("Exception occurred: {}", ex.getMessage());
            throw ex;
        }

        logRequestDetails(wrappedRequest);

        String responseBody = new String(wrappedResponse.getContentAsByteArray(), StandardCharsets.UTF_8);
        int status = wrappedResponse.getStatus();

        try {
            Object json = objectMapper.readValue(responseBody, Object.class);
            String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);

            if (status >= 400) {
                requestLogger.info("Request fail with status: {} {}", status, uri);
                exceptionLogger.error("\n{}", prettyJson);
            }
            else {
                responseLogger.info("\n{}", prettyJson);
            }
        } catch (Exception ex) {
            if (status >= 400) {
                requestLogger.info("Request Fail with status: {} {}", status, uri);
                exceptionLogger.error("Non json error response: {}", responseBody);
            }
            else {
                responseLogger.info(responseBody);
            }
        }
        wrappedResponse.copyBodyToResponse();
    }

    private void logRequestDetails(ContentCachingRequestWrapper requestWrapper) {
        String method = requestWrapper.getMethod();
        String uri = requestWrapper.getRequestURI();
        String requestBody = "";

        if ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method) || "PATCH".equalsIgnoreCase(method)) {
            byte[] buf = requestWrapper.getContentAsByteArray();

            if (buf.length > 0) {
                requestBody = new String(buf, StandardCharsets.UTF_8);
            }
        }

        if (!requestBody.isEmpty()) {
            try {
                Object object = objectMapper.readValue(requestBody, Object.class);
                String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
                requestLogger.info("{} {}\n{}", method, uri, json);
            } catch (Exception ex) {
                requestLogger.info("{} {}\n{}", method, uri, requestBody);
            }
        }
        else {
            requestLogger.info("{} {}", method, uri);
        }
    }
}
