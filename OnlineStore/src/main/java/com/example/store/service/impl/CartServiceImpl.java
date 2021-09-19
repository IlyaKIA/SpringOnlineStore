package com.example.store.service.impl;

import com.example.store.domain.Product;
import com.example.store.repository.Cart;
import com.example.store.service.CartService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final Cart cart;

    public CartServiceImpl(Cart cart) {
        this.cart = cart;
    }

    @Override
    public Map<Product, Long> getProductsFromCart(){
        List<Product> cartList = cart.getProducts();
        Map<Product, Long> idProductMap = new HashMap<>();
        if(cartList != null) {
            for (Product product : cartList) {
                idProductMap.merge(product, 1L, Long::sum);
            }
        }
        return idProductMap;
    }

    @Override
    public void setProductInCart(Product addingProduct){
        cart.setProduct(addingProduct);
    }

    @Override
    public Integer cartSum() {
        return cart.getProducts().stream().map(Product::getPrice).reduce(0, Integer::sum);
    }
}
