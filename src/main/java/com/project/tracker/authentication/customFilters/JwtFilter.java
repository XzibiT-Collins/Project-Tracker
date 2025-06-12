package com.project.tracker.authentication.customFilters;

import com.project.tracker.authentication.jwtService.JwtService;
import com.project.tracker.exceptions.customExceptions.ExpiredJwtTokenException;
import com.project.tracker.exceptions.customExceptions.GeneralJwtException;
import com.project.tracker.services.UsersDetailServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
//    ApplicationContext applicationContext;
    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtService jwtService,
//                     ApplicationContext applicationContext,
                     UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
//        this.applicationContext = applicationContext;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String userName = null;
        String token = null;

        //TODO remove this
        if (authHeader == null) {
            System.out.println("No Authorization header present.");
        } else {
            System.out.println("Authorization header: " + authHeader);
        }

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            System.out.print("Token: " + token);
            userName = jwtService.extractUserName(token);
            System.out.println("User Name: " + userName);
        }

        try {
            //Try to get user if not in the security context
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

//                        applicationContext
//                        .getBean(UsersDetailServiceImpl.class)
//                        .loadUserByUsername(userName);

                System.out.println("User Found: " + userDetails.getUsername());

                //validate token
                if (jwtService.validateToken(token, userDetails)) {
                    System.out.println("Token Validated");
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null,
                                    userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    System.out.println("Token Invalid");
                }
            }
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtTokenException("Token Expired: " + e.getMessage());
        }catch (Exception e){
            throw new GeneralJwtException("Error: " + e.getMessage());
        } finally {
            //continue chain
            filterChain.doFilter(request, response);
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.startsWith("/api/v1/auth") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/oauth2") ||
                path.startsWith("/login") ||
                path.startsWith("/error");
    }
}
