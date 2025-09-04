package com.example.studentmanagement.jwt;

import com.example.studentmanagement.service.mysql.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final JwtService jwtService;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // 7 refer to a word Bearer and one space
            // Ex: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9... so it means removes that prefix (Bearer ) and returns only the JWT token.
            token = authHeader.substring(7);
            email = jwtService.extractUsername(token);
        }

        // check if valid email and not yet authenticated
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // get user info by email
            UserDetails userDetails = userService.loadUserByUsername(email);

            // check valid token and belong to that user
            if (jwtService.validateToken(token, userDetails)) {
                                                                                                                // user info , no need password cuz authenticated via token, role
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // extra: ip address, sessionId...
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
