package kr.co.devMart.service;

import kr.co.devMart.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CartService {
    private final CartMapper cartMapper;

    @Autowired
    public CartService(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    public List<Map<String, Object>> getCartListByUser(Map<String, Object> params) {
        return cartMapper.selectCartListByUser(params);
    }
    public int addCart(Map<String, Object> params) {
        return cartMapper.insertCart(params);
    }
    public int updateCartQuantity(Map<String, Object> params) {
        return cartMapper.updateCartQuantity(params);
    }
    public int deleteCart(Map<String, Object> params) {
        return cartMapper.deleteCart(params);
    }
    public int deleteCartByUser(Map<String, Object> params) {
        return cartMapper.deleteCartByUser(params);
    }
}
