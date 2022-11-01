package com.mmbackendassignment.mmbackendassignment.config;

import com.mmbackendassignment.mmbackendassignment.repository.UserRepository;
import com.mmbackendassignment.mmbackendassignment.security.JwtRequestFilter;
import com.mmbackendassignment.mmbackendassignment.security.JwtService;
import com.mmbackendassignment.mmbackendassignment.security.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
public class SecurityConfig {

    private final UserRepository userRepo;
    private final JwtService jwtService;

    public SecurityConfig(UserRepository userRepo, JwtService jwtService) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder encoder, UserDetailsService udService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(udService)
                .passwordEncoder(encoder)
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new MyUserDetailsService( this.userRepo );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() //Disables basic auth
                .authorizeRequests()

                // AUTH
                .antMatchers("/auth").permitAll()

                // USER
                .antMatchers(HttpMethod.GET,"/users").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/users").permitAll() // create user
                .antMatchers(HttpMethod.PUT, "/users/password").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.PUT, "/users/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/users/**").hasAuthority("ADMIN")

                // PROFILE
                .antMatchers(HttpMethod.GET, "/profiles/**").hasAnyAuthority("CLIENT", "ADMIN")
                .antMatchers(HttpMethod.POST, "/profiles/**").hasAnyAuthority("CLIENT", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/profiles/**").hasAnyAuthority("CLIENT", "ADMIN")
                .antMatchers(HttpMethod.POST, "/profiles/**").hasAnyAuthority("CLIENT", "ADMIN")

                // CLIENT
                .antMatchers(HttpMethod.GET,"/clients/**").hasAnyAuthority("CLIENT", "ADMIN")

                // OWNER
                .antMatchers(HttpMethod.GET,"/owners/**").hasAnyAuthority("OWNER", "ADMIN")

                // ADDRESS
                .antMatchers(HttpMethod.GET,"/addresses").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST,"/addresses/**").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.PUT,"/addresses/**").hasAuthority("OWNER")
                .antMatchers(HttpMethod.DELETE,"/addresses/**").hasAuthority("OWNER")

                // FIELD
                .antMatchers(HttpMethod.GET,"/fields/**").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST,"/fields/**").hasAnyAuthority("OWNER")
                .antMatchers(HttpMethod.PUT,"/fields/**").hasAuthority("OWNER")
                .antMatchers(HttpMethod.DELETE,"/fields/**").hasAnyAuthority("OWNER", "ADMIN")

                // CONTRACT
                .antMatchers(HttpMethod.GET,"/contracts/**").permitAll()
                .antMatchers(HttpMethod.POST,"/contracts/**").hasAnyAuthority("CLIENT")
                .antMatchers(HttpMethod.PUT,"/contracts/client/**").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.PUT,"/contracts/owner/**").hasAuthority("OWNER")
                .antMatchers(HttpMethod.DELETE,"/contracts/**").permitAll()

                .and()
                .addFilterBefore(new JwtRequestFilter(jwtService, userDetailsService()), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable() //Because of JWT security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }
}
