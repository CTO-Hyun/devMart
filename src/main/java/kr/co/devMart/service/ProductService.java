package kr.co.devMart.service;

import kr.co.devMart.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private ProductMapper productMapper;

    public List<Map<String, Object>> getProductList(Map params) {
        return productMapper.selectProductList(params);
    }
}