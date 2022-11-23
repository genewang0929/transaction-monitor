package com.chunhanwang.service;

import com.chunhanwang.entity.*;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

import java.security.*;
import java.util.*;
import java.util.stream.*;

@Service
public class JWTService {
    @Autowired
    private AuthenticationManager authenticationManager;

    private final String KEY = "SynpulseBackendAndItShouldHaveVeryLongString";

    public String generateToken(AuthRequest request){
        // 1. 驗證帳密是否正確
        // AuthenticationManager 支援多種身分驗證方式，執行時會接收 Authentication 介面的物件。
        // UsernamePasswordAuthenticationToken 其建構子的第一、二個參數接受任何型態（object）的物件。前者為 principal，代表與伺服器進行互動的人；後者為
        // credentials，是用來證明自己是 principal 的一種資料，通常是指密碼。
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getIban(), request.getPassword());
//        System.out.println("email is "+request.getEmail());
//        System.out.println("PW is "+request.getPassword());
        authentication = authenticationManager.authenticate(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 2. 產生JWT
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR,24);

        // generate Claims obj into JWT's payload
        Claims claims = Jwts.claims();
        claims.put("email",userDetails.getUsername());
        claims.setExpiration(calendar.getTime()); // 到期時間 -> RFC 7519 標準的建議內容
        claims.setIssuer("NoteShareBackend"); // 核發者 -> RFC 7519 標準的建議內容

        // generate secret key
        Key secretKey = Keys.hmacShaKeyFor(KEY.getBytes());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey)
                .compact();
    }

    public Map<String, Object> parseToken(String token) {
        Key secretKey = Keys.hmacShaKeyFor(KEY.getBytes());

        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();

        Claims claims = parser
                .parseClaimsJws(token)
                .getBody();

        return claims.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
