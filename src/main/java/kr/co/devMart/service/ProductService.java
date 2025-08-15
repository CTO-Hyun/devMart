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

    public List<Map<String, Object>> getProductList(Map<String, Object> params) {
        return productMapper.selectProductList(params);
    }
    
    public Map<String, Object> getProductById(Map<String, Object> params) {
        return productMapper.selectProductById(params);
    }
    
    public int createProduct(Map<String, Object> params) {
        return productMapper.insertProduct(params);
    }
    
    public int updateProduct(Map<String, Object> params) {
        return productMapper.updateProduct(params);
    }
    
    public int deleteProduct(Map<String, Object> params) {
        return productMapper.deleteProduct(params);
    }
    
    public List<Map<String, Object>> getProductByCategory(Map<String, Object> params) {
        return productMapper.selectProductByCategory(params);
    }
    
    public int getProductCount(Map<String, Object> params) {
        return productMapper.selectProductCount(params);
    }
    
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