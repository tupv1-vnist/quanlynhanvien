package com.net.usermanager.jwt;


import com.net.usermanager.security.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private String getJwtFromRequest(HttpServletRequest request){
        String bearerToken=request.getHeader("Authorization");
        //Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken)&&bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
            return null;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            // Lấy jwt từ request
            String jwt =getJwtFromRequest(request);
            if (StringUtils.hasText(jwt)&& jwtTokenProvider.validateToken(jwt)){
                // Lấy userName từ chuỗi jwt
                String userName=jwtTokenProvider.getUserNameFromJwt(jwt);
                //Lấy thông tin người dùng từ userID
                UserDetails userDetails= customUserDetailsService.loadUserByUsername(userName);
                if (userDetails!=null){
                    //Nếu người dùng hợp lệ set thông tin cho security context
                    UsernamePasswordAuthenticationToken authentication =new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }
            }
        }catch (Exception ex){
            log.error("fail on set authentication",ex);
        }
        filterChain.doFilter(request,response);
    }
}
