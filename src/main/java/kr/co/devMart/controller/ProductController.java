package kr.co.devMart.controller;

import kr.co.devMart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    @GetMapping("/list")
    public void productList(ModelMap modelMap, @RequestParam Map params) {
        modelMap.addAttribute("products", productService.getProductList(params));
    }
    @GetMapping("/index")
    public void index(ModelMap modelMap, @RequestParam Map params) {
    }
}