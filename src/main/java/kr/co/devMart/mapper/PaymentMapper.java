package kr.co.devMart.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PaymentMapper {
    List<Map<String, Object>> selectPaymentListByUser(Map<String, Object> params);

    Map<String, Object> selectPaymentDetail(Map<String, Object> params);

    int insertPayment(Map<String, Object> params);

    int updatePaymentStatus(Map<String, Object> params);
}
