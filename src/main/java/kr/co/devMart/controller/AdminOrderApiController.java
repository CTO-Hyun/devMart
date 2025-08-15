package kr.co.devMart.controller;

import kr.co.devMart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/orders")
public class AdminOrderApiController {
    private final OrderService orderService;

    @Autowired
    public AdminOrderApiController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 주문 목록
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Map<String, Object>> getOrderList(@RequestParam Map<String, Object> params) {
        return orderService.getOrderListByUser(params);
    }

    // 주문 등록
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int createOrder(@RequestBody Map<String, Object> params) {
        return orderService.createOrder(params);
    }

    // 주문 수정(상태변경)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public int updateOrder(@RequestBody Map<String, Object> params) {
        return orderService.updateOrderStatus(params);
    }

    // 주문 삭제
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public int deleteOrder(@RequestBody Map<String, Object> params) {
        return orderService.deleteOrder(params);
    }

    // 주문 상세
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Map<String, Object> getOrderDetail(@RequestParam Map<String, Object> params) {
        return orderService.getOrderDetail(params);
    }
}
