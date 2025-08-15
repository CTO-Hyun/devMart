package kr.co.devMart.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    List<Map<String, Object>> selectOrderListByUser(Map<String, Object> params);
    Map<String, Object> selectOrderDetail(Map<String, Object> params);
    int insertOrder(Map<String, Object> params);
    int insertOrderItem(Map<String, Object> params);
    int updateOrderStatus(Map<String, Object> params);
    int deleteOrder(Map<String, Object> params);
}
