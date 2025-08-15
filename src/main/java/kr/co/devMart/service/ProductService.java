package kr.co.devMart.service;

import kr.co.devMart.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    // 상품 목록 조회
    public List<Map<String, Object>> getProductList(Map<String, Object> params) {
        return productMapper.selectProductList(params);
    }

    // 상품 상세 조회
    public Map<String, Object> getProductById(Map<String, Object> params) {
        return productMapper.selectProductById(params);
    }

    // 상품 등록
    public int createProduct(Map<String, Object> params) {
        return productMapper.insertProduct(params);
    }

    // 상품 수정
    public int updateProduct(Map<String, Object> params) {
        return productMapper.updateProduct(params);
    }

    // 상품 삭제
    public int deleteProduct(Map<String, Object> params) {
        return productMapper.deleteProduct(params);
    }

    // 카테고리별 상품 목록 조회
    public List<Map<String, Object>> getProductByCategory(Map<String, Object> params) {
        return productMapper.selectProductByCategory(params);
    }

    // 상품 개수 조회
    public int getProductCount(Map<String, Object> params) {
        return productMapper.selectProductCount(params);
    }

    // 페이징 처리된 상품 목록 조회
    public List<Map<String, Object>> getProductListWithPaging(Map<String, Object> params) {
        // 페이징 처리
        int page = (int) params.getOrDefault("page", 1);
        int size = (int) params.getOrDefault("size", 10);
        int offset = (page - 1) * size;

        Map<String, Object> queryParams = new HashMap<>(params);
        queryParams.put("limit", size);
        queryParams.put("offset", offset);

        return productMapper.selectProductList(queryParams);
    }
}