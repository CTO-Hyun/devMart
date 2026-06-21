package kr.co.devMart.controller;

import kr.co.devMart.service.CartService;
import kr.co.devMart.common.auth.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getCartList() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long userSeq = currentUserSeq(auth);
        if (userSeq == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "LOGIN_REQUIRED"));
        }
        Map<String, Object> params = new HashMap<>();
        params.put("userSeq", userSeq);
        return ResponseEntity.ok(cartService.getCartListByUser(params));
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addCart(@RequestBody Map<String, Object> params) {
        Map<String, Object> res = new HashMap<>();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Long userSeq = currentUserSeq(auth);
            if (userSeq == null) {
                res.put("success", false);
                res.put("message", "LOGIN_REQUIRED");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
            }
            params.put("userSeq", userSeq);
            int result = cartService.addCart(params);
            res.put("success", result > 0);
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }
        return ResponseEntity.ok(res);
    }

    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> updateCartQuantity(@RequestBody Map<String, Object> params) {
        Map<String, Object> res = new HashMap<>();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Long userSeq = currentUserSeq(auth);
            if (userSeq == null) {
                res.put("success", false);
                res.put("message", "LOGIN_REQUIRED");
                return res;
            }
            params.put("userSeq", userSeq);
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
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Long userSeq = currentUserSeq(auth);
            if (userSeq == null) {
                res.put("success", false);
                res.put("message", "LOGIN_REQUIRED");
                return res;
            }
            params.put("userSeq", userSeq);
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
        Long userSeq = currentUserSeq(auth);
        if (userSeq == null) {
            return 0;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("userSeq", userSeq);
        int deleted = cartService.deleteCartByUser(params);
        return deleted;
    }

    private Long currentUserSeq(Authentication auth) {
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return null;
        }
        Object principal = auth.getPrincipal();
        if (principal instanceof CustomUserDetails userDetails) {
            return userDetails.getUserSeq();
        }
        return null;
    }
}
