package kr.co.devMart.service;

import kr.co.devMart.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReviewService {
    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewService(ReviewMapper reviewMapper) {
        this.reviewMapper = reviewMapper;
    }

    // 리뷰 목록 조회 (사용자별)
    public List<Map<String, Object>> getReviewListByUser(Map<String, Object> params) {
        return reviewMapper.selectReviewListByUser(params);
    }

    // 상품별 리뷰 목록 조회
    public List<Map<String, Object>> getReviewListByProduct(Map<String, Object> params) {
        return reviewMapper.selectReviewListByProduct(params);
    }

    // 상품별 리뷰 개수 조회 (페이징)
    public int getReviewCountByProduct(Map<String, Object> params) {
        return reviewMapper.selectReviewCountByProduct(params);
    }

    // 리뷰 등록
    public int addReview(Map<String, Object> params) {
        return reviewMapper.insertReview(params);
    }

    // 리뷰 수정
    public int updateReview(Map<String, Object> params) {
        return reviewMapper.updateReview(params);
    }

    // 리뷰 삭제
    public int deleteReview(Map<String, Object> params) {
        return reviewMapper.deleteReview(params);
    }
}
