package kr.co.devMart.controller;

import kr.co.devMart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/users")
public class AdminUserApiController {
    private final UserService userService;

    @Autowired
    public AdminUserApiController(UserService userService) {
        this.userService = userService;
    }

    // 회원 목록
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Map<String, Object>> getUserList(@RequestParam Map<String, Object> params) {
        return userService.getUserList(params);
    }

    // 회원 등록
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int createUser(@RequestBody Map<String, Object> params) {
        return userService.createUser(params);
    }

    // 회원 수정
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public int updateUser(@RequestBody Map<String, Object> params) {
        return userService.updateUser(params);
    }

    // 회원 삭제
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public int deleteUser(@RequestBody Map<String, Object> params) {
        return userService.deleteUser(params);
    }

    // 회원 상세
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Map<String, Object> getUserDetail(@RequestParam Map<String, Object> params) {
        return userService.getUserDetail(params);
    }
}
