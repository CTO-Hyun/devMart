package kr.co.devMart.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface ReviewMapper {
    List<Map<String, Object>> selectReviewListByUser(Map<String, Object> params);
    List<Map<String, Object>> selectReviewListByProduct(Map<String, Object> params);
    int insertReview(Map<String, Object> params);
    int updateReview(Map<String, Object> params);
    int deleteReview(Map<String, Object> params);
}
