package com.example.store.controller;

import com.example.store.domain.Product;
import com.example.store.domain.authentication.UserProfile;
import com.example.store.service.CartService;
import com.example.store.service.ProductService;
import com.example.store.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
@RequestMapping("/shop/cart")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ProductService productService;
    private final UserProfileService userProfileService;

    @GetMapping
    public String getProducts(Model model) {
        if(cartService.getProductsFromCart().isEmpty()) return "redirect:/shop";

        //Authentication check and forwarding user info
        if (authCheck().isPresent()) model.addAttribute("userProfile", authCheck().orElse(new UserProfile()));
        model.addAttribute("products", cartService.getProductsFromCart());
        model.addAttribute("cartSum", cartService.cartSum());
        return "cart";
    }

    @PostMapping("/add-product-to-cart")
    public RedirectView saveNewProduct(@RequestParam(value = "id") Long id) {
        cartService.setProductInCart(productService.findById(id).orElse(new Product()));
        return new RedirectView ("/shop");
    }

    @PostMapping("/delete")
    public String deleteProductFromCartPost(@RequestParam(value = "id") Long id){
        cartService.deleteProductFromCart(id);
        return "redirect:/shop/cart";
    }

    private Optional<UserProfile> authCheck (){
        return userProfileService.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
