package kr.co.devMart.service;

import kr.co.devMart.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderMapper orderMapper;

    @Autowired
    public OrderService(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    // 주문 목록 조회 (사용자별)
    public List<Map<String, Object>> getOrderListByUser(Map<String, Object> params) {
        return orderMapper.selectOrderListByUser(params);
    }

    // 주문 상세 조회
    public Map<String, Object> getOrderDetail(Map<String, Object> params) {
        return orderMapper.selectOrderDetail(params);
    }

    // 주문 생성
    @Transactional
    public int createOrder(Map<String, Object> params) {
        log.info("[주문생성] params={}", params);
        try {
            int result = orderMapper.insertOrder(params);
            log.info("[주문생성] 성공, result={}", result);
            return result;
        } catch (Exception e) {
            log.error("[주문생성] 실패", e);
            throw e;
        }
    }

    // 주문상품 추가
    @Transactional
    public int addOrderItem(Map<String, Object> params) {
        log.info("[주문상품추가] params={}", params);
        try {
            int result = orderMapper.insertOrderItem(params);
            log.info("[주문상품추가] 성공, result={}", result);
            return result;
        } catch (Exception e) {
            log.error("[주문상품추가] 실패", e);
            throw e;
        }
    }

    // 주문 상태 변경
    public int updateOrderStatus(Map<String, Object> params) {
        return orderMapper.updateOrderStatus(params);
    }

    // 주문 삭제
    public int deleteOrder(Map<String, Object> params) {
        return orderMapper.deleteOrder(params);
    }
}
