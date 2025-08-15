package kr.co.devMart.service;

import kr.co.devMart.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    private final OrderMapper orderMapper;

    @Autowired
    public OrderService(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public List<Map<String, Object>> getOrderListByUser(Map<String, Object> params) {
        return orderMapper.selectOrderListByUser(params);
    }
    public Map<String, Object> getOrderDetail(Map<String, Object> params) {
        return orderMapper.selectOrderDetail(params);
    }
    public int createOrder(Map<String, Object> params) {
        return orderMapper.insertOrder(params);
    }
    public int addOrderItem(Map<String, Object> params) {
        return orderMapper.insertOrderItem(params);
    }
    public int updateOrderStatus(Map<String, Object> params) {
        return orderMapper.updateOrderStatus(params);
    }
    public int deleteOrder(Map<String, Object> params) {
        return orderMapper.deleteOrder(params);
    }
}
