package org.example.medilinkspring.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
public class AuthController {
    //로그인 , 회원가입 서비스

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @PostMapping("/join")
    public String join(@RequestBody User user) {
        log.debug("회원가입 접근 {}",user);

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userService.createUser(user);
        return "회원가입완료";
    }

    @PostMapping("/login")
    public String lgoin() {
        return "로그인완료";
    }
}
