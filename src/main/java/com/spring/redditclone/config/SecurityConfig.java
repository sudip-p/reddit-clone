package com.spring.redditclone.config;

import com.spring.redditclone.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * WebSecurityConfigurerAdapter is the base class that provides our default security
 * configurations, which we can override in the configure() method.
 */
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * AuthenticationManager is used in AuthService, however AuthenticationManager is an interface in Spring and has
     * multiple implementations. Therefore, we create a bean to specify which implementation to use.
     * @return
     * @throws Exception
     */
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
    /**
     * Override this method as it is part of the WebSecurityConfigurerAdapter class.
     * Disables csrf protection, because REST APIs are stateless by definition and
     * we use JSON web tokens for authorization. CSRF attacks happen mostly with
     * applications using sessions cookies to authenticate session information.
     *
     * @param httpSecurity Security object passed in to disable csrf and authorize
     *                     incoming requests
     */
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()                                    // authorize incoming requests to backend API
                .antMatchers("/api/auth/**")                // whose endpoint URL starts matches with "/api/auth/**"
                .permitAll()
                .anyRequest()
                .authenticated();                                       // make sure any other URL should be authenticated
        httpSecurity.addFilterBefore(jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
