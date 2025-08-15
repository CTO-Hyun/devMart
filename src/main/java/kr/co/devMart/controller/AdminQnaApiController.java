package kr.co.devMart.controller;

import kr.co.devMart.service.QnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/qna")
public class AdminQnaApiController {
    private final QnaService qnaService;

    @Autowired
    public AdminQnaApiController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    // 문의 목록
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Map<String, Object>> getQnaList(@RequestParam Map<String, Object> params) {
        return qnaService.getQnaListByUser(params);
    }

    // 문의 등록
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int createQna(@RequestBody Map<String, Object> params) {
        return qnaService.addQna(params);
    }

    // 문의 수정
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public int updateQna(@RequestBody Map<String, Object> params) {
        return qnaService.updateQna(params);
    }

    // 문의 삭제
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public int deleteQna(@RequestBody Map<String, Object> params) {
        return qnaService.deleteQna(params);
    }

    // 문의 상세 (필요시 구현)
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Map<String, Object> getQnaDetail(@RequestParam Map<String, Object> params) {
        // 상세 조회 로직 필요시 구현
        return new HashMap<>();
    }
}
