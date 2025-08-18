package kr.co.devMart.service;

import kr.co.devMart.mapper.QnaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QnaService {
    private final QnaMapper qnaMapper;

    @Autowired
    public QnaService(QnaMapper qnaMapper) {
        this.qnaMapper = qnaMapper;
    }

    // 문의 목록 조회 (사용자별)
    public List<Map<String, Object>> getQnaListByUser(Map<String, Object> params) {
        return qnaMapper.selectQnaListByUser(params);
    }

    // 상품별 문의 목록 조회
    public List<Map<String, Object>> getQnaListByProduct(Map<String, Object> params) {
        return qnaMapper.selectQnaListByProduct(params);
    }

    // 상품별 문의 전체 개수 조회 (페이징)
    public int getQnaCountByProduct(Map<String, Object> params) {
        return qnaMapper.selectQnaCountByProduct(params);
    }

    // 문의 등록
    public int addQna(Map<String, Object> params) {
        return qnaMapper.insertQna(params);
    }

    // 문의 수정
    public int updateQna(Map<String, Object> params) {
        return qnaMapper.updateQna(params);
    }

    // 문의 삭제
    public int deleteQna(Map<String, Object> params) {
        return qnaMapper.deleteQna(params);
    }
}
