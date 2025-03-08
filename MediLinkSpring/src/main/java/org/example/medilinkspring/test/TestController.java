package org.example.medilinkspring.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    
    @GetMapping("/test")
    public String test(){
        return "테스트확인";
    }

    @GetMapping("/api/manager")
    public String test2(){
        return "테스트확인2";
    }
}
