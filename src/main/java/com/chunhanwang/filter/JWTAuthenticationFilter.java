//package com.chunhanwang.filter;
//
//import com.chunhanwang.service.*;
//import org.springframework.beans.factory.annotation.*;
//import org.springframework.http.*;
//import org.springframework.security.authentication.*;
//import org.springframework.security.core.*;
//import org.springframework.security.core.context.*;
//import org.springframework.security.core.userdetails.*;
//import org.springframework.stereotype.*;
//import org.springframework.web.filter.*;
//
//import javax.servlet.*;
//import javax.servlet.http.*;
//import java.io.*;
//import java.util.*;
//
//@Component
//public class JWTAuthenticationFilter extends OncePerRequestFilter {
//    @Autowired
//    private JWTService jwtService;
//    @Autowired
//    private SpringUserService springUserService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        // get header and parse token from "Bearer ". (from RFC 6750)
//        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//
//        if (authHeader != null) {
//            String accessToken = authHeader.replace("Bearer ", "");
//
//            Map<String, Object> claims = jwtService.parseToken(accessToken);
//            String iban = (String) claims.get("iban");
//            UserDetails userDetails = springUserService.loadUserByUsername(iban);
//
//            Authentication authentication =
//                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
