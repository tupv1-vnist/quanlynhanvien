package com.net.usermanager.jwt;


import com.net.usermanager.security.CustomUserDetails;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    @Value("${ra.jwt.secret}")
    private String JWT_SECRET;
    @Value("${ra.jwt.expiration}")
    private int JWT_EXPIRATION;
    //Tạo ra jwt từ thông tin của user
    public String generateToken(CustomUserDetails customUserDetails){
        Date now=new Date();
        Date dateExpired =new Date(now.getTime()+JWT_EXPIRATION);
        // Tạo chuỗi JWT từ userName
        return Jwts.builder()
                .setSubject(customUserDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(dateExpired  )
                .signWith(SignatureAlgorithm.HS512,JWT_SECRET).compact();
    }

    // Lấy thông tin user từ jwt
    public String getUserNameFromJwt(String token){
        Claims claims=Jwts.parser().setSigningKey(JWT_SECRET)
                .parseClaimsJws(token).getBody();
        // Trả lại thông tin username
        return claims.getSubject();
    }

    //Valifate thông tin JWT
    public  boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException ex){
            log.error("Invalid JWT Token");
        }catch (ExpiredJwtException ex){
            log.error("Experid JWT Token");
        }catch (UnsupportedJwtException ex){
            log.error("Unsuppoted JWT Token");
        }catch (IllegalArgumentException ex){
            log.error("JWT claims String is empty");
        }
        return false;
    }
}
