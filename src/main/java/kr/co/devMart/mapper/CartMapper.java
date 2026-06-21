package kr.co.devMart.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CartMapper {
    List<Map<String, Object>> selectCartListByUser(Map<String, Object> params);

    Map<String, Object> selectCartItem(Map<String, Object> params);

    int insertCart(Map<String, Object> params);

    int increaseCartQuantity(Map<String, Object> params);

    int updateCartQuantity(Map<String, Object> params);

    int deleteCart(Map<String, Object> params);

    int deleteCartByUser(Map<String, Object> params);
}
