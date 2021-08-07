package org.example.context.repository.impl;

import org.example.context.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Repository
@Scope(scopeName = SCOPE_PROTOTYPE)
public class CartImpl implements Cart {

    private List<Product> cart = new ArrayList<>();

    @Autowired
    public CartImpl(List<Product> cart) {
        this.cart = cart;
    }

    @Override
    public String addProductToCart(Product product) {
        cart.add(product);
        return "Success";
    }

    @Override
    public String delProductFromCart(int id) {
        for (Product p: cart) {
            if (p.getId() == id){
                cart.remove(p);
                return "Success";
            }
        }
        return "fail";
    }

    @Override
    public List<Product> cartList() {
        return cart;
    }
}
