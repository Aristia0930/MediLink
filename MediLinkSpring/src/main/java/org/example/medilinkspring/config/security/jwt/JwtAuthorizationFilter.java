package org.example.medilinkspring.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.medilinkspring.config.security.PrincipalDetails;
import org.example.medilinkspring.user.entity.User;
import org.example.medilinkspring.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final Dotenv dotenv = Dotenv.load();

    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getRequestURI().startsWith("/idcheck")||request.getRequestURI().startsWith("/join")||request.getRequestURI().startsWith("/login")) {

            chain.doFilter(request, response);
            return;
        }
        System.out.println("인증 권한 요구된 요청입니다.");
        log.debug("인증 요구 접근");
//        super.doFilterInternal(request, response, chain);

        String jwtHeader = request.getHeader("Authorization"); //토큰
        log.debug("jwt 헤더 {} ",jwtHeader);


        //헤더가 있는지 확인
        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }

        //받은 토큰으로 정상적인 사용자 인지 확인한다.
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", ""); //토큰만 꺼내기
        String jwtSecret = dotenv.get("JWT_VALUE");
        String username = JWT.require(Algorithm.HMAC512(jwtSecret)).build().verify(jwtToken)
                .getClaim("username").asString();

        if (username != null) {
            User user = userRepository.findByName(username);

            PrincipalDetails principalDetails = new PrincipalDetails(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    principalDetails, // 나중에 컨트롤러에서 DI해서 쓸 때 사용하기 편함.
                    null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
                    principalDetails.getAuthorities());

            // 강제로 시큐리티의 세션에 접근하여 값 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        
        log.debug("인증 요청 진행 완료");

        chain.doFilter(request, response);
    }
}