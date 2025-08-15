package kr.co.devMart.service;

import kr.co.devMart.mapper.PaymentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class PaymentService {
    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
    private final PaymentMapper paymentMapper;

    @Autowired
    public PaymentService(PaymentMapper paymentMapper) {
        this.paymentMapper = paymentMapper;
    }

    // 결제 목록 조회 (사용자별)
    public List<Map<String, Object>> getPaymentListByUser(Map<String, Object> params) {
        return paymentMapper.selectPaymentListByUser(params);
    }

    // 결제 상세 조회
    public Map<String, Object> getPaymentDetail(Map<String, Object> params) {
        return paymentMapper.selectPaymentDetail(params);
    }

    // 결제 생성
    @Transactional
    public int createPayment(Map<String, Object> params) {
        log.info("[결제생성] params={}", params);
        try {
            int result = paymentMapper.insertPayment(params);
            log.info("[결제생성] 성공, result={}", result);
            return result;
        } catch (Exception e) {
            log.error("[결제생성] 실패", e);
            throw e;
        }
    }

    // 결제 상태 변경
    public int updatePaymentStatus(Map<String, Object> params) {
        return paymentMapper.updatePaymentStatus(params);
    }
}
