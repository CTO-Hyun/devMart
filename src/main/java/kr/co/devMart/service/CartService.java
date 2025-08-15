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

    // 장바구니 목록 조회 (사용자별)
    public List<Map<String, Object>> getCartListByUser(Map<String, Object> params) {
        return cartMapper.selectCartListByUser(params);
    }

    // 장바구니 추가
    public int addCart(Map<String, Object> params) {
        return cartMapper.insertCart(params);
    }

    // 장바구니 수량 수정
    public int updateCartQuantity(Map<String, Object> params) {
        return cartMapper.updateCartQuantity(params);
    }

    // 장바구니 항목 삭제
    public int deleteCart(Map<String, Object> params) {
        return cartMapper.deleteCart(params);
    }

    // 사용자별 장바구니 전체 비우기
    public int deleteCartByUser(Map<String, Object> params) {
        return cartMapper.deleteCartByUser(params);
    }
}
