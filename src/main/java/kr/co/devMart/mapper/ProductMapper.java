package kr.co.devMart.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductMapper {

    List<Map<String, Object>> selectProductList(Map<String, Object> params);

    Map<String, Object> selectProductById(Map<String, Object> params);

    int insertProduct(Map<String, Object> params);

    int updateProduct(Map<String, Object> params);

    int deleteProduct(Map<String, Object> params);

    List<Map<String, Object>> selectProductByCategory(Map<String, Object> params);

    int selectProductCount(Map<String, Object> params);
}