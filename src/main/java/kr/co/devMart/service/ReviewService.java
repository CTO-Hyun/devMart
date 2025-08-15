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
    public List<Map<String, Object>> getReviewListByUser(Map<String, Object> params) {
        return reviewMapper.selectReviewListByUser(params);
    }
    public List<Map<String, Object>> getReviewListByProduct(Map<String, Object> params) {
        return reviewMapper.selectReviewListByProduct(params);
    }
    public int addReview(Map<String, Object> params) {
        return reviewMapper.insertReview(params);
    }
    public int updateReview(Map<String, Object> params) {
        return reviewMapper.updateReview(params);
    }
    public int deleteReview(Map<String, Object> params) {
        return reviewMapper.deleteReview(params);
    }
}
