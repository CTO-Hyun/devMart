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
    public List<Map<String, Object>> getQnaListByUser(Map<String, Object> params) {
        return qnaMapper.selectQnaListByUser(params);
    }
    public List<Map<String, Object>> getQnaListByProduct(Map<String, Object> params) {
        return qnaMapper.selectQnaListByProduct(params);
    }
    public int addQna(Map<String, Object> params) {
        return qnaMapper.insertQna(params);
    }
    public int updateQna(Map<String, Object> params) {
        return qnaMapper.updateQna(params);
    }
    public int deleteQna(Map<String, Object> params) {
        return qnaMapper.deleteQna(params);
    }
}
