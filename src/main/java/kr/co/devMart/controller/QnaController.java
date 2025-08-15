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

    // 문의 등록
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int addQna(@RequestBody Map<String, Object> params) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        params.put("username", auth.getName());
        return qnaService.addQna(params);
    }

    // 문의 수정
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public int updateQna(@RequestBody Map<String, Object> params) {
        return qnaService.updateQna(params);
    }

    // 문의(Q&A) 삭제
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public int deleteQna(@RequestBody Map<String, Object> params) {
        return qnaService.deleteQna(params);
    }
}
