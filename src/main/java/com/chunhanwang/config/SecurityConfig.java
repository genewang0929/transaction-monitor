package com.chunhanwang.config;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.web.cors.*;

import java.util.*;

@EnableWebSecurity // 這個標記已經含有 @Configuration標記了
// 注入(@Autowired) UserDetailsService, Spring 會自動找到有實作這個介面的類別，也就是 SpringUserService
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    @Lazy
//    private UserDetailsService userDetailsService;
//    @Autowired
//    @Lazy
//    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Override // setting the rules of API's authentication
    protected void configure(HttpSecurity http) throws Exception {
        //「/users」這個 API 及其底下的所有 GET 請求，需通過身份驗證才可存取。
        // 其餘 API 的所有GET請求，允許所有呼叫方存取。
        //「/users」這個 API 的 POST 請求，允許所有呼叫方存取。
        // 其餘的所有 API，需通過身份驗證才可存取。
        // authorizeRequests 方法開始自訂授權規則。使用 antMatchers 方法，傳入 HTTP 請求方法與 API 路徑，後面接著授權方式，
        http
                .authorizeHttpRequests()
                .antMatchers(HttpMethod.POST, "/verification/login").permitAll()
                .antMatchers(HttpMethod.POST, "/verification/parse").permitAll()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
//                .anyRequest().permitAll() // tmp for testing
//                .anyRequest().authenticated() // 對剩下的 API 定義規則
//                .and()
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .cors().and();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(new BCryptPasswordEncoder());
//    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000/"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("Access-Control-Allow-Headers", "Access-Control-Allow-Origin",
                "Access-Control-Request-Method", "Access-Control-Request-Headers", "Origin", "Cache-Control", "Content-Type", "Authorization"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
