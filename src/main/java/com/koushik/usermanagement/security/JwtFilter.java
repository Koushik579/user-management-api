package com.koushik.usermanagement.security;

import com.koushik.usermanagement.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    public static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    JwtService jwtService;
    public JwtFilter(JwtService jwtService){
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request
            ,HttpServletResponse response
            ,FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        if(path.startsWith("/auth/")){
            filterChain.doFilter(request,response);
            return;
        }

        String header = request.getHeader("Authorization");
        String token = null;
        if(header != null && header.startsWith("Bearer ")){
            token = header.substring(7);
        }
        if (token == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Missing or Expired Token\"}");
            return;
        }

        if(jwtService.isTokenValid(token)){
            String email = jwtService.extractEmail(token);
            if (email == null){
                log.warn("Extracted email from token is null");
            }
            if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
                String role = jwtService.extractRole(token);
                List<SimpleGrantedAuthority> authorities =
                        List.of(new SimpleGrantedAuthority("ROLE_"+role));
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(email,null,authorities);
                SecurityContextHolder.getContext().setAuthentication(authToken);
                log.info("Authentication is set in security context");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or Expired Token");
            log.warn("Invalid or Expired Token");
            return;
        }
        filterChain.doFilter(request,response);
    }
}
