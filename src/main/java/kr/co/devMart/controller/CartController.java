package kr.co.devMart.controller;

import kr.co.devMart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public int addCart(@RequestBody Map<String, Object> params) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        params.put("userId", auth.getName());
        return cartService.addCart(params);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public int updateCartQuantity(@RequestBody Map<String, Object> params) {
        return cartService.updateCartQuantity(params);
    }

    // 장바구니 항목 삭제
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public int deleteCart(@RequestBody Map<String, Object> params) {
        return cartService.deleteCart(params);
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
