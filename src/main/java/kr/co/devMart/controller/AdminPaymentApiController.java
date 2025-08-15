package kr.co.devMart.controller;

import kr.co.devMart.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/payments")
public class AdminPaymentApiController {
    private final PaymentService paymentService;

    @Autowired
    public AdminPaymentApiController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // 결제 목록
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Map<String, Object>> getPaymentList(@RequestParam Map<String, Object> params) {
        return paymentService.getPaymentListByUser(params);
    }

    // 결제 등록
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int createPayment(@RequestBody Map<String, Object> params) {
        return paymentService.createPayment(params);
    }

    // 결제 수정(상태변경)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public int updatePayment(@RequestBody Map<String, Object> params) {
        return paymentService.updatePaymentStatus(params);
    }

    // 결제 삭제 (실제 DB 삭제가 필요하다면 PaymentService/Mapper에 메서드 추가 필요)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public int deletePayment(@RequestBody Map<String, Object> params) {
        // 결제 삭제 로직 필요시 구현
        return 1;
    }

    // 결제 상세
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Map<String, Object> getPaymentDetail(@RequestParam Map<String, Object> params) {
        return paymentService.getPaymentDetail(params);
    }
}
