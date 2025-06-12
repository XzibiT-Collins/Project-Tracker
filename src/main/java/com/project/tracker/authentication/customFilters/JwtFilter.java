package com.project.tracker.authentication.customFilters;

import com.project.tracker.authentication.jwtService.JwtService;
import com.project.tracker.services.UsersDetailServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    ApplicationContext applicationContext;

    public JwtFilter(JwtService jwtService,
                     ApplicationContext applicationContext) {
        this.jwtService = jwtService;
        this.applicationContext = applicationContext;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String userName = null;
        String token = null;

        //TODO remove this
        System.out.println("Auth Header" + authHeader);
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            System.out.print("Token: " + token);
            userName = jwtService.extractUserName(token);
            System.out.println("User Name: " + userName);
        }

        //Try to get user if not in the security context
        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = applicationContext
                    .getBean(UsersDetailServiceImpl.class)
                    .loadUserByUsername(userName);

            System.out.println("User Found: " + userDetails.getUsername());

            //validate token
            if(jwtService.validateToken(token, userDetails)){
                System.out.println("Token Validated");
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null,
                                userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }else{
                System.out.println("Token Invalid");
            }
        }

        //continue chain
        filterChain.doFilter(request, response);
    }
}
