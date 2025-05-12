package com.example.SalesProject.filter;

import java.util.Collections;

import com.example.SalesProject.entity.Users;
import com.example.SalesProject.repository.UserRepository;
import com.example.SalesProject.util.JwtUtil;
import com.example.SalesProject.util.TokenBlacklist;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final TokenBlacklist tokenBlacklist;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // Check if the Authorization header is present and starts with Bearer
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (tokenBlacklist.isBlacklisted(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            String email = jwtUtil.extractEmail(token);
            String role = jwtUtil.extractRole(token);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Users user = userRepository.findByEmail(email).orElse(null);

                // Validate token and user
                if (user != null && jwtUtil.validateToken(token, email)) {
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null,
                            Collections.singletonList(authority));

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
