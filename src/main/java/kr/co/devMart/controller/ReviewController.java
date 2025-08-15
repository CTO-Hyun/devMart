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
    @GetMapping("/list")
    public List<Map<String, Object>> getMyReviews() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> params = new HashMap<>();
        params.put("username", auth.getName());
        return reviewService.getReviewListByUser(params);
    }
    // 상품별 리뷰 목록
    @GetMapping("/product")
    public List<Map<String, Object>> getProductReviews(@RequestParam Map<String, Object> params) {
        return reviewService.getReviewListByProduct(params);
    }
    // 리뷰 등록
    @PostMapping("/create")
    public int addReview(@RequestBody Map<String, Object> params) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        params.put("username", auth.getName());
        return reviewService.addReview(params);
    }
    // 리뷰 수정
    @PostMapping("/update")
    public int updateReview(@RequestBody Map<String, Object> params) {
        return reviewService.updateReview(params);
    }
    // 리뷰 삭제
    @PostMapping("/delete")
    public int deleteReview(@RequestBody Map<String, Object> params) {
        return reviewService.deleteReview(params);
    }
}
