package kr.co.devMart.service;

import kr.co.devMart.mapper.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PaymentService {
    private final PaymentMapper paymentMapper;

    @Autowired
    public PaymentService(PaymentMapper paymentMapper) {
        this.paymentMapper = paymentMapper;
    }

    public List<Map<String, Object>> getPaymentListByUser(Map<String, Object> params) {
        return paymentMapper.selectPaymentListByUser(params);
    }
    public Map<String, Object> getPaymentDetail(Map<String, Object> params) {
        return paymentMapper.selectPaymentDetail(params);
    }
    public int createPayment(Map<String, Object> params) {
        return paymentMapper.insertPayment(params);
    }
    public int updatePaymentStatus(Map<String, Object> params) {
        return paymentMapper.updatePaymentStatus(params);
    }
}
