package kr.co.devMart.controller;

import kr.co.devMart.service.QnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/qna")
public class QnaController {
    @Autowired
    private QnaService qnaService;

    // 마이페이지: 내 문의 목록
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Map<String, Object>> getMyQna() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> params = new HashMap<>();
        params.put("username", auth.getName());
        return qnaService.getQnaListByUser(params);
    }

    // 상품별 문의 목록
    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public List<Map<String, Object>> getProductQna(@RequestParam Map<String, Object> params) {
        return qnaService.getQnaListByProduct(params);
    }

    @PostMapping("/create")
    @ResponseBody
    public Map<String, Object> addQna(@RequestParam Map<String, Object> params) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        params.put("userName", auth.getName());
        int result = qnaService.addQna(params);
        Map<String, Object> res = new HashMap<>();
        res.put("success", result > 0);
        return res;
    }

    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> updateQna(@RequestParam Map<String, Object> params) {
        int result = qnaService.updateQna(params);
        Map<String, Object> res = new HashMap<>();
        res.put("success", result > 0);
        return res;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> deleteQna(@RequestParam Map<String, Object> params) {
        int result = qnaService.deleteQna(params);
        Map<String, Object> res = new HashMap<>();
        res.put("success", result > 0);
        return res;
    }
}
