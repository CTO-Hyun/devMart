package kr.co.devMart.controller;

import kr.co.devMart.service.QnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kr.co.devMart.common.auth.CustomUserDetails;

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

    // 상품별 문의 목록 (페이징 지원)
    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public Map<String, Object> getProductQna(@RequestParam Map<String, Object> params) {
        int page = 1;
        int size = 10;
        if (params.get("page") != null) page = Integer.parseInt(params.get("page").toString());
        if (params.get("size") != null) size = Integer.parseInt(params.get("size").toString());
        params.put("offset", (page - 1) * size);
        params.put("limit", size);
        List<Map<String, Object>> qnaList = qnaService.getQnaListByProduct(params);
        int totalCount = qnaService.getQnaCountByProduct(params);
        Map<String, Object> result = new HashMap<>();
        result.put("qnaList", qnaList);
        result.put("totalCount", totalCount);
        return result;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addQna(@RequestBody Map<String, Object> params) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            params.put("username", auth.getName());
            if (principal instanceof CustomUserDetails) {
                params.put("userId", ((CustomUserDetails) principal).getId());
            }
        }
        int result = qnaService.addQna(params);
        Map<String, Object> res = new HashMap<>();
        res.put("success", result > 0);
        return res;
    }

    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> updateQna(@RequestBody Map<String, Object> params) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            params.put("username", auth.getName());
            if (principal instanceof CustomUserDetails) {
                params.put("userId", ((CustomUserDetails) principal).getId());
            }
        }
        int result = qnaService.updateQna(params);
        Map<String, Object> res = new HashMap<>();
        res.put("success", result > 0);
        return res;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> deleteQna(@RequestBody Map<String, Object> params) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            params.put("userId", ((CustomUserDetails) principal).getId());
        }
        int result = qnaService.deleteQna(params);
        Map<String, Object> res = new HashMap<>();
        res.put("success", result > 0);
        return res;
    }
}
