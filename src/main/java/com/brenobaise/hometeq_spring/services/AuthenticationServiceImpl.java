package com.brenobaise.hometeq_spring.services;

import com.brenobaise.hometeq_spring.dtos.auth.AuthResponse;
import com.brenobaise.hometeq_spring.dtos.auth.SignUpRequest;
import com.brenobaise.hometeq_spring.entities.User;
import com.brenobaise.hometeq_spring.security.AppUserDetails;
import com.brenobaise.hometeq_spring.security.AuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    @Value("${jwt.secret}")
    private  String secretKey;
    @Getter
    public final Long jwtExpiryMs = 60000L;

    @Override
    public UserDetails authenticate(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,password)
        );
        return userDetailsService.loadUserByUsername(email);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("testkey", "testvalue");
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiryMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact(); // convert to string
    }

    /**
     * converts secret key into bytes and creates a new SecretKey
     * @return HMAC-SHA Key
     */
    private Key getSigningKey() {
        byte[] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public UserDetails validateToken(String token) {
        String username = extractUsernameFromToken(token);
        return userDetailsService.loadUserByUsername(username);
    }

    public AuthResponse registerAndAuthenticateUser(SignUpRequest request){
        User user = userService.registerUser(request);
        UserDetails userDetails = new AppUserDetails(user);
        String token = generateToken(userDetails);
        return new AuthResponse(token,getJwtExpiryMs() /1000);

    }

    private String extractUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return  claims.getSubject();
    }
}
