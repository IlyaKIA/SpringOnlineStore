package com.example.store.controller;

import com.example.store.domain.Product;
import com.example.store.service.CartService;
import com.example.store.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.swing.text.html.Option;
import java.util.Optional;

@Controller
@RequestMapping("/shop/cart")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ProductService productService;

    @GetMapping
    public String getProducts(Model model) {
        model.addAttribute("products", cartService.getProductsFromCart());
        return "cart";
    }

    @PostMapping("/add-product-to-cart")
    public RedirectView saveNewProduct(@RequestParam(value = "id") Long id) {
        cartService.setProductInCart(productService.findById(id).orElse(new Product()));
        return new RedirectView ("/shop");
    }
}
