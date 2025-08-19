package kr.co.devMart.controller;

import kr.co.devMart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import kr.co.devMart.common.auth.CustomUserDetails;

@Controller
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 상품 목록
    @RequestMapping(value = "/product/{path}", method = RequestMethod.GET)
    public void productList(@PathVariable String path, ModelMap modelMap, @RequestParam Map<String, Object> params) {
        if (params.get("categoryType") == null) {
            params.put("title", "전체");
        } else if (params.get("categoryType").equals("ACC")) {
            params.put("title", "악세사리");
        } else if (params.get("categoryType").equals("BAG")) {
            params.put("title", "가방");
        } else if (params.get("categoryType").equals("ETC")) {
            params.put("title", "기타");
        } else if (params.get("categoryType").equals("KIDS")) {
            params.put("title", "아동");
        } else if (params.get("categoryType").equals("MEN")) {
            params.put("title", "남성복");
        } else if (params.get("categoryType").equals("SHOES")) {
            params.put("title", "신발");
        } else if (params.get("categoryType").equals("SPORTS")) {
            params.put("title", "운동복");
        } else if (params.get("categoryType").equals("WOMEN")) {
            params.put("title", "여성복");
        }
        modelMap.addAttribute("params", params);
        modelMap.addAttribute("products", productService.getProductList(params));
    }

    // 상품 상세 (파라미터 방식)
    @RequestMapping(value = "/product/detail", method = RequestMethod.GET)
    public String productDetailParam(@RequestParam(value = "productSeq") Long productSeq, ModelMap modelMap) {
        Map<String, Object> params = new HashMap<>();
        params.put("productSeq", productSeq);
        Map<String, Object> product = productService.getProductById(params);
        if (product == null) {
            modelMap.addAttribute("errorMsg", "상품을 찾을 수 없습니다.");
            return "product/detail";
        }
        // 로그인 사용자 userSeq 내려주기
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        Long loginUserId = null;
        String loginUserName = "";
        if (principal instanceof kr.co.devMart.common.auth.CustomUserDetails) {
            loginUserId = ((kr.co.devMart.common.auth.CustomUserDetails) principal).getUserSeq();
            loginUserName = ((kr.co.devMart.common.auth.CustomUserDetails) principal).getUsername();
        }
        params.put("loginUserId", loginUserId);
        params.put("loginUserName", loginUserName);
        modelMap.addAttribute("loginUserId", loginUserId);
        modelMap.addAttribute("loginUserName", loginUserName);
        modelMap.addAttribute("product", product);
        return "product/detail";
    }

    // 상품 상세 (PathVariable 방식)
    @RequestMapping(value = "/product/detail/{id}", method = RequestMethod.GET)
    public String productDetail(@PathVariable Long id, ModelMap modelMap) {
        Map<String, Object> params = new HashMap<>();
        params.put("productSeq", id);
        Map<String, Object> product = productService.getProductById(params);
        if (product == null) {
            modelMap.addAttribute("errorMsg", "상품을 찾을 수 없습니다.");
            return "product/detail";
        }
        // 로그인 사용자 userSeq 내려주기
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        Long loginUserId = null;
        String loginUserName = "";
        if (principal instanceof kr.co.devMart.common.auth.CustomUserDetails) {
            loginUserId = ((kr.co.devMart.common.auth.CustomUserDetails) principal).getUserSeq();
            loginUserName = ((kr.co.devMart.common.auth.CustomUserDetails) principal).getUsername();
        }
        params.put("loginUserId", loginUserId);
        params.put("loginUserName", loginUserName);
        modelMap.addAttribute("loginUserId", loginUserId);
        modelMap.addAttribute("loginUserName", loginUserName);
        modelMap.addAttribute("product", product);
        return "product/detail";
    }

    // 상품 등록 폼
    @RequestMapping(value = "/product/create", method = RequestMethod.GET)
    public String productCreateForm() {
        return "product/create";
    }

    // 상품 등록
    @RequestMapping(value = "/product/create", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createProduct(@RequestParam Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();

        try {
            int count = productService.createProduct(params);
            if (count > 0) {
                result.put("success", true);
                result.put("message", "상품이 성공적으로 등록되었습니다.");
            } else {
                result.put("success", false);
                result.put("message", "상품 등록에 실패했습니다.");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "오류가 발생했습니다: " + e.getMessage());
        }

        return result;
    }

    // 상품 수정 폼
    @RequestMapping(value = "/product/update/{id}", method = RequestMethod.GET)
    public String productUpdateForm(@PathVariable Long id, ModelMap modelMap) {
        Map<String, Object> params = new HashMap<>();
        params.put("productSeq", id);

        Map<String, Object> product = productService.getProductById(params);
        modelMap.addAttribute("product", product);
        return "product/update";
    }

    // 상품 수정
    @RequestMapping(value = "/product/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateProduct(@RequestParam Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();

        try {
            int count = productService.updateProduct(params);
            if (count > 0) {
                result.put("success", true);
                result.put("message", "상품이 성공적으로 수정되었습니다.");
            } else {
                result.put("success", false);
                result.put("message", "상품 수정에 실패했습니다.");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "오류가 발생했습니다: " + e.getMessage());
        }

        return result;
    }

    // 상품 삭제
    @RequestMapping(value = "/product/delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteProduct(@RequestParam Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();

        try {
            int count = productService.deleteProduct(params);
            if (count > 0) {
                result.put("success", true);
                result.put("message", "상품이 성공적으로 삭제되었습니다.");
            } else {
                result.put("success", false);
                result.put("message", "상품 삭제에 실패했습니다.");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "오류가 발생했습니다: " + e.getMessage());
        }

        return result;
    }

    // 상품 API (프론트용)
    @RequestMapping(value = "/api/products", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProductsApi(@RequestParam Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();

        try {
            List<Map<String, Object>> products = productService.getProductListWithPaging(params);
            int totalCount = productService.getProductCount(params);

            result.put("success", true);
            result.put("data", products);
            result.put("totalCount", totalCount);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "오류가 발생했습니다: " + e.getMessage());
        }

        return result;
    }

    // 메인
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String priorOrderPlnPopup(@RequestParam Map<String, Object> params) {
        return "main/main";
    }
}