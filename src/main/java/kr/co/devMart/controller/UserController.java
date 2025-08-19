package kr.co.devMart.controller;

import kr.co.devMart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    // 회원가입
    @PostMapping("/signup")
    @ResponseBody
    public Map<String, Object> signup(@RequestBody Map<String, Object> params) {
        userService.signup(params);
        Map<String, Object> res = new HashMap<>();
        res.put("success", true);
        return res;
    }

    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> updateUser(@RequestParam Map<String, Object> params) {
        int result = userService.updateUser(params);
        Map<String, Object> res = new HashMap<>();
        res.put("success", result > 0);
        return res;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> deleteUser(@RequestParam Map<String, Object> params) {
        int result = userService.deleteUser(params);
        Map<String, Object> res = new HashMap<>();
        res.put("success", result > 0);
        return res;
    }
}
