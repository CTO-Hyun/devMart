package kr.co.devMart.controller;

import kr.co.devMart.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * 결제 내역 조회
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Map<String, Object>> getPaymentList(@RequestParam Map params) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        params.put("userId", auth.getName());
        return paymentService.getPaymentListByUser(params);
    }

    /**
     * 결제 상세 조회
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Map<String, Object> getPaymentDetail(@RequestParam Map params) {
        return paymentService.getPaymentDetail(params);
    }

    /**
     * 결제 생성(모의 결제)
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int createPayment(@RequestBody Map params) {
        return paymentService.createPayment(params);
    }

    /**
     * 결제 상태 변경
     */
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    public int updatePaymentStatus(@RequestBody Map params) {
        return paymentService.updatePaymentStatus(params);
    }
}
