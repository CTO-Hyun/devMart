package kr.co.devMart.controller;

import kr.co.devMart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/api/products")
public class AdminProductApiController {
    private final ProductService productService;

    @Autowired
    public AdminProductApiController(ProductService productService) {
        this.productService = productService;
    }

    // 상품 목록 (관리자)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Map<String, Object>> getProductList(@RequestParam Map<String, Object> params) {
        return productService.getProductList(params);
    }

    // 상품 등록 (관리자)
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int createProduct(@RequestBody Map<String, Object> params) {
        return productService.createProduct(params);
    }

    // 상품 수정 (관리자)
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public int updateProduct(@RequestBody Map<String, Object> params) {
        return productService.updateProduct(params);
    }

    // 상품 삭제 (관리자)
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public int deleteProduct(@RequestBody Map<String, Object> params) {
        return productService.deleteProduct(params);
    }
}
