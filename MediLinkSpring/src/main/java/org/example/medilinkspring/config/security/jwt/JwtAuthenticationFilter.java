package org.example.medilinkspring.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.medilinkspring.config.security.PrincipalDetails;
import org.example.medilinkspring.user.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final Dotenv dotenv = Dotenv.load();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        log.debug("로그인 필터");
        try {


            //제이슨 데이터 파싱을 위해 사용
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);


            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword());
            log.debug("토큰생성 완료 : {}",token);

            Authentication authentication = authenticationManager.authenticate(token);

            return authentication;
        } catch (IOException e) {
            throw new RuntimeException("로그인 요청 처리 중 오류 발생", e); // JSON 파싱 오류 시 예외 처리
        } catch (AuthenticationException e) {
            // 인증 실패 시 예외 처리
            log.error("AuthenticationException 발생 {}",e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            return null; // 인증 실패 시 null 반환 (기본적으로 Spring Security에서 처리됨)
        } catch (Exception e) {
            log.error("예상못한 예외 발생 {}",e.getMessage());
            e.printStackTrace();
            return null;
        }


    }


    //attemptAuthentication 실행후 실행되는곳 인증이 정상적으로 도달 하면 실행 됨 그렇기에 여기서 jwt 토큰을 만들어줌 요청한 사용자에게 응답으로 전성해주면됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.debug("인증 완료 successfulAuthentication 실행");

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        //Hash 방식 이거 말고도 rsa 방식도 존재한다.
        String jwtSecret = dotenv.get("JWT_VALUE");
        String jwtToken= JWT.create()
                .withSubject(principalDetails.getUsername())//토큰이름
                .withExpiresAt(new Date(System.currentTimeMillis()+(60000*10)))//만료 시간 60000 이 1분
                .withClaim("id",principalDetails.getUser().getId())
                .withClaim("username",principalDetails.getUser().getName())
                .sign(Algorithm.HMAC512(jwtSecret));//내 고유의값.

        response.addHeader("Authorization","Bearer "+jwtToken);
        //이렇게 사용자에게 jwt 토큰을 보냈으면 이제 이 토큰이 유효한지 확인하는 필터가 필요함
    }
}