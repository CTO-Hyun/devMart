package kr.co.devMart.controller;

import kr.co.devMart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * 주문 목록 조회
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Map<String, Object>> getOrderList(@RequestParam Map<String, Object> params) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        params.put("userId", auth.getName());
        return orderService.getOrderListByUser(params);
    }

    /**
     * 주문 상세 조회
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Map<String, Object> getOrderDetail(@RequestParam Map<String, Object> params) {
        return orderService.getOrderDetail(params);
    }

    @PostMapping("/create")
    @ResponseBody
    public Map<String, Object> createOrder(@RequestBody Map<String, Object> params) {
        Map<String, Object> res = new HashMap<>();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            params.put("userId", auth.getName());
            int result = orderService.createOrder(params);
            res.put("success", result > 0);
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", e.getMessage());
        }
        return res;
    }

    @PostMapping("/addItem")
    @ResponseBody
    public Map<String, Object> addOrderItem(@RequestBody Map<String, Object> params) {
        Map<String, Object> res = new HashMap<>();
        try {
            int result = orderService.addOrderItem(params);
            res.put("success", result > 0);
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", e.getMessage());
        }
        return res;
    }

    @PostMapping("/cancel")
    @ResponseBody
    public Map<String, Object> cancelOrder(@RequestBody Map<String, Object> params) {
        Map<String, Object> res = new HashMap<>();
        try {
            int result = orderService.updateOrderStatus(params);
            res.put("success", result > 0);
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", e.getMessage());
        }
        return res;
    }

    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> updateOrderStatus(@RequestBody Map<String, Object> params) {
        Map<String, Object> res = new HashMap<>();
        try {
            int result = orderService.updateOrderStatus(params);
            res.put("success", result > 0);
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", e.getMessage());
        }
        return res;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> deleteOrder(@RequestBody Map<String, Object> params) {
        Map<String, Object> res = new HashMap<>();
        try {
            int result = orderService.deleteOrder(params);
            res.put("success", result > 0);
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", e.getMessage());
        }
        return res;
    }
}
