package kr.co.devMart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CartPageController {
    @RequestMapping(value = "/cart.html", method = RequestMethod.GET)
    public String cartPage() {
        return "cart";
    }
}
