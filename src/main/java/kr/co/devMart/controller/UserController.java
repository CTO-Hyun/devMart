package kr.co.devMart.controller;

import kr.co.devMart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public void signup(@RequestBody Map<String, Object> params) {
        userService.signup(params);
    }
}
