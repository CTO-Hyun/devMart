package kr.co.devMart.controller;

import kr.co.devMart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    /**
     * 주문 생성
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int createOrder(@RequestBody Map<String, Object> params) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        params.put("userId", auth.getName());
        return orderService.createOrder(params);
    }

    /**
     * 주문상품 추가
     */
    @RequestMapping(value = "/addItem", method = RequestMethod.POST)
    public int addOrderItem(@RequestBody Map<String, Object> params) {
        return orderService.addOrderItem(params);
    }

    /**
     * 주문 취소
     */
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public int cancelOrder(@RequestBody Map<String, Object> params) {
        return orderService.updateOrderStatus(params);
    }

    /**
     * 주문 상태 수정
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public int updateOrderStatus(@RequestBody Map<String, Object> params) {
        return orderService.updateOrderStatus(params);
    }

    /**
     * 주문 삭제
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public int deleteOrder(@RequestBody Map<String, Object> params) {
        return orderService.deleteOrder(params);
    }
}
