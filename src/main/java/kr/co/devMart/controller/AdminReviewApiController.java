package kr.co.devMart.controller;

import kr.co.devMart.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/reviews")
public class AdminReviewApiController {
    private final ReviewService reviewService;

    @Autowired
    public AdminReviewApiController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // 리뷰 목록
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Map<String, Object>> getReviewList(@RequestParam Map<String, Object> params) {
        return reviewService.getReviewListByUser(params);
    }

    // 리뷰 등록
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int createReview(@RequestBody Map<String, Object> params) {
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

    // 리뷰 상세 (필요시 구현)
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Map<String, Object> getReviewDetail(@RequestParam Map<String, Object> params) {
        // 상세 조회 로직 필요시 구현
        return new HashMap<>();
    }
}
