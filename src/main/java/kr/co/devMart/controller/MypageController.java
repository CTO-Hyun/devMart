package kr.co.devMart.controller;

import kr.co.devMart.service.UserService;
import kr.co.devMart.service.OrderService;
import kr.co.devMart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mypage")
public class MypageController {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    // 회원정보 조회
    @GetMapping("/user")
    public Map<String, Object> getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userService.getUserInfo(username);
    }

    // 주문내역 조회
    @GetMapping("/orders")
    public List<Map<String, Object>> getOrderList() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> params = new HashMap<>();
        params.put("username", auth.getName());
        return orderService.getOrderListByUser(params);
    }

    // 장바구니 조회
    @GetMapping("/cart")
    public List<Map<String, Object>> getCartList() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> params = new HashMap<>();
        params.put("username", auth.getName());
        return cartService.getCartListByUser(params);
    }

    // 리뷰, Q&A 등은 추후 구현 예정
}
