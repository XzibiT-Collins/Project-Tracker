package com.project.tracker.authentication;

import com.project.tracker.authentication.customFilters.JwtFilter;
import com.project.tracker.authentication.oauth2Service.CustomOauthSuccessHandler;
import com.project.tracker.authentication.oauth2Service.CustomOidcUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final UserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;
    private final CustomOauthSuccessHandler customOauthSuccessHandler;
    private final CustomOidcUserService customOidcUserService;

    public SecurityConfiguration(
            UserDetailsService userDetailsService,
            JwtFilter jwtFilter,
            CustomOauthSuccessHandler customOauthSuccessHandler,
            CustomOidcUserService customOidcUserService) {
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
        this.customOauthSuccessHandler = customOauthSuccessHandler;
        this.customOidcUserService = customOidcUserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(request ->
                request.requestMatchers("/api/v1/auth/login").permitAll()
                        .requestMatchers("/api/v1/auth/register").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()

                        .requestMatchers("/oauth2/**").permitAll()
                        .requestMatchers("/login/oauth2/**").permitAll()
                        .requestMatchers("/").permitAll() // Add root paths

                        .anyRequest().authenticated())
                .sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                .oauth2Login(
                        oauth2 -> oauth2
                                .userInfoEndpoint(user -> user
                                        .oidcUserService(customOidcUserService))
                                .successHandler(customOauthSuccessHandler)
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    //set custom auth providers
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    //set custom password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
