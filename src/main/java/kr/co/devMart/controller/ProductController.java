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
    private static final List<Map<String, String>> PRODUCT_CATEGORIES = List.of(
            Map.of("code", "", "name", "전체"),
            Map.of("code", "WOMEN", "name", "여성복"),
            Map.of("code", "MEN", "name", "남성복"),
            Map.of("code", "SHOES", "name", "신발"),
            Map.of("code", "BAG", "name", "가방"),
            Map.of("code", "ACC", "name", "악세사리"),
            Map.of("code", "KIDS", "name", "아동"),
            Map.of("code", "SPORTS", "name", "운동복"),
            Map.of("code", "ETC", "name", "기타")
    );
    private static final Map<String, String> SORT_COLUMNS = Map.of(
            "new", "CREATE_DATE",
            "popular", "SELL_COUNT",
            "rating", "RATING",
            "priceLow", "PRICE",
            "priceHigh", "PRICE"
    );

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 상품 목록
    @RequestMapping(value = "/product/{path}", method = RequestMethod.GET)
    public void productList(@PathVariable String path, ModelMap modelMap, @RequestParam Map<String, Object> params) {
        String categoryType = String.valueOf(params.getOrDefault("categoryType", ""));
        params.put("title", getCategoryName(categoryType));
        applySafeSort(params);
        int page = parsePositiveInt(params.get("page"), 1);
        int size = parsePositiveInt(params.get("size"), 40);
        int totalCount = productService.getProductCount(params);
        int totalPages = Math.max((int) Math.ceil((double) totalCount / size), 1);
        if (page > totalPages) {
            page = totalPages;
        }
        params.put("page", page);
        params.put("size", size);
        params.put("limit", size);
        params.put("offset", (page - 1) * size);
        int pageBlockSize = 10;
        int startPage = ((page - 1) / pageBlockSize) * pageBlockSize + 1;
        int endPage = Math.min(startPage + pageBlockSize - 1, totalPages);
        modelMap.addAttribute("params", params);
        modelMap.addAttribute("categories", PRODUCT_CATEGORIES);
        modelMap.addAttribute("currentCategory", categoryType);
        modelMap.addAttribute("currentSort", params.get("sort"));
        modelMap.addAttribute("currentSearchKeyword", String.valueOf(params.getOrDefault("searchKeyword", "")));
        modelMap.addAttribute("products", productService.getProductList(params));
        modelMap.addAttribute("totalCount", totalCount);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("totalPages", totalPages);
        modelMap.addAttribute("startPage", startPage);
        modelMap.addAttribute("endPage", endPage);
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
        modelMap.addAttribute("categories", PRODUCT_CATEGORIES);
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
        modelMap.addAttribute("categories", PRODUCT_CATEGORIES);
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

    private String getCategoryName(String categoryType) {
        return PRODUCT_CATEGORIES.stream()
                .filter(category -> category.get("code").equals(categoryType))
                .findFirst()
                .map(category -> category.get("name"))
                .orElse("전체");
    }

    private void applySafeSort(Map<String, Object> params) {
        String sort = String.valueOf(params.getOrDefault("sort", "new"));
        if (!SORT_COLUMNS.containsKey(sort)) {
            sort = "new";
        }
        params.put("sort", sort);
        params.put("sortBy", SORT_COLUMNS.get(sort));
        params.put("sortOrder", "priceLow".equals(sort) ? "ASC" : "DESC");
    }

    private int parsePositiveInt(Object value, int defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        try {
            int parsed = Integer.parseInt(value.toString());
            return parsed > 0 ? parsed : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
