package kr.co.devMart.controller;

import kr.co.devMart.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    // 마이페이지: 내 리뷰 목록
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Map<String, Object>> getMyReviews() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> params = new HashMap<>();
        params.put("username", auth.getName());
        return reviewService.getReviewListByUser(params);
    }

    // 상품별 리뷰 목록 (페이징 지원)
    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public Map<String, Object> getProductReviews(@RequestParam Map<String, Object> params) {
        int page = 1;
        int size = 10;
        if (params.get("page") != null) page = Integer.parseInt(params.get("page").toString());
        if (params.get("size") != null) size = Integer.parseInt(params.get("size").toString());
        params.put("offset", (page - 1) * size);
        params.put("limit", size);
        List<Map<String, Object>> reviews = reviewService.getReviewListByProduct(params);
        int totalCount = reviewService.getReviewCountByProduct(params);
        Map<String, Object> result = new HashMap<>();
        result.put("reviews", reviews);
        result.put("totalCount", totalCount);
        return result;
    }

    // 리뷰 등록
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int addReview(@RequestBody Map<String, Object> params) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        params.put("userName", auth.getName());
        return reviewService.addReview(params);
    }

    // 리뷰 수정
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public int updateReview(@RequestBody Map<String, Object> params) {
        return reviewService.updateReview(params);
    }

    // 리뷰 삭제
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public int deleteReview(@RequestBody Map<String, Object> params) {
        return reviewService.deleteReview(params);
    }
}
