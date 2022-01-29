package com.sample.spring.config;

import java.util.ArrayList;
// import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class WebAuthenticationProvider implements AuthenticationProvider {
    private final Logger logger = LogManager.getLogger(WebAuthenticationProvider.class);
    // private @Autowired HttpServletRequest request;
    
    // @Autowired
    // private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userId = authentication.getName();
        String userPassword = authentication.getCredentials().toString();
        // Long projectId = Long.parseLong(request.getParameterValues("projectid")[0]);

        // User user = this.userRepository.findByUserId(userId);

        // 비밀번호 확인
        // PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Boolean isPassword = false;
        try {
            // isPassword = passwordEncoder.matches(userPassword, user.getUserPassword());
            if(userPassword.equals("1234")) isPassword = true;
        } catch (Exception e) {
            isPassword = false;
        }
        
        if(userId == null) {
            throw new UsernameNotFoundException("계정이 존재하지 않습니다."); 
        }
        else if(!isPassword) {
            throw new BadCredentialsException("잘못된 비밀번호 입니다."); 
        }

        // 권한 토큰 생성
        if (userId.equals("admin")) {
            ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

            logger.info(authorities);
    
            UsernamePasswordAuthenticationToken aToken = (
                new UsernamePasswordAuthenticationToken(
                    // user, 
                    null,
                    authorities
                )
            );
    
            logger.info("login success for user : " + authentication.getName());
            
            return aToken;
        }
        else {
            throw new BadCredentialsException("user not found"); // 비밀번호 일치 실패
        }
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    
}