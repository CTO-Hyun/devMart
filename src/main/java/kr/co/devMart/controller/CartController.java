package kr.co.devMart.controller;

import kr.co.devMart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Map<String, Object>> getCartList() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        return cartService.getCartListByUser(params);
    }

    @PostMapping("/add")
    @ResponseBody
    public Map<String, Object> addCart(@RequestBody Map<String, Object> params) {
        Map<String, Object> res = new HashMap<>();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            params.put("userId", auth.getName());
            int result = cartService.addCart(params);
            res.put("success", result > 0);
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", e.getMessage());
        }
        return res;
    }

    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> updateCartQuantity(@RequestBody Map<String, Object> params) {
        Map<String, Object> res = new HashMap<>();
        try {
            int result = cartService.updateCartQuantity(params);
            res.put("success", result > 0);
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", e.getMessage());
        }
        return res;
    }

    // 장바구니 항목 삭제
    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> deleteCart(@RequestBody Map<String, Object> params) {
        Map<String, Object> res = new HashMap<>();
        try {
            int result = cartService.deleteCart(params);
            res.put("success", result > 0);
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", e.getMessage());
        }
        return res;
    }

    @RequestMapping(value = "/clear", method = RequestMethod.POST)
    public int clearCart() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        int deleted = cartService.deleteCartByUser(params);
        System.out.println("[CartController] clearCart: userId=" + userId + ", deletedRows=" + deleted);
        return deleted;
    }
}
