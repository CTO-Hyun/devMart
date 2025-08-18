package kr.co.devMart.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface QnaMapper {
    List<Map<String, Object>> selectQnaListByUser(Map<String, Object> params);

    List<Map<String, Object>> selectQnaListByProduct(Map<String, Object> params);

    int insertQna(Map<String, Object> params);

    int updateQna(Map<String, Object> params);

    int deleteQna(Map<String, Object> params);

    int selectQnaCountByProduct(Map<String, Object> params);
}
