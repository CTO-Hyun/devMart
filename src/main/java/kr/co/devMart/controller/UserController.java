package kr.co.devMart.controller;

import kr.co.devMart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    // 회원가입
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public void signup(@RequestBody Map<String, Object> params) {
        userService.signup(params);
    }
}
