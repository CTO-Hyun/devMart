package kr.co.devMart.controller;

import kr.co.devMart.service.CartService;
import kr.co.devMart.common.auth.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CartPageController {
    private final CartService cartService;

    @Autowired
    public CartPageController(CartService cartService) {
        this.cartService = cartService;
    }

    @RequestMapping(value = "/cart.html", method = RequestMethod.GET)
    public String cartPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long userSeq = currentUserSeq(auth);
        Map<String, Object> params = new HashMap<>();
        params.put("userSeq", userSeq);
        List<Map<String, Object>> cartList = cartService.getCartListByUser(params);
        int totalCount = 0;
        int totalPrice = 0;
        for (Map<String, Object> item : cartList) {
            int qty = 0;
            int price = 0;
            try {
                qty = Integer.parseInt(item.get("quantity").toString());
                price = Integer.parseInt(item.get("price").toString());
            } catch (Exception e) {}
            totalCount += qty;
            totalPrice += price * qty;
        }
        model.addAttribute("cartList", cartList);
        model.addAttribute("cartCount", totalCount);
        model.addAttribute("cartTotal", totalPrice);
        return "cart";
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
