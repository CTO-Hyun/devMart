package kr.co.devMart.controller;

import kr.co.devMart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/list")
    public List<Map<String, Object>> getCartList() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        return cartService.getCartListByUser(params);
    }

    @PostMapping("/add")
    public int addCart(@RequestBody Map<String, Object> params) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        params.put("userId", auth.getName());
        return cartService.addCart(params);
    }

    @PostMapping("/update")
    public int updateCartQuantity(@RequestBody Map<String, Object> params) {
        return cartService.updateCartQuantity(params);
    }

    // 장바구니 항목 삭제
    @PostMapping("/delete")
    public int deleteCart(@RequestBody Map<String, Object> params) {
        return cartService.deleteCart(params);
    }

    @PostMapping("/clear")
    public int clearCart() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> params = new HashMap<>();
        params.put("userId", auth.getName());
        return cartService.deleteCartByUser(params);
    }
}
