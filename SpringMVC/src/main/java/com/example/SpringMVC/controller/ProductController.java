package com.example.SpringMVC.controller;

import com.example.SpringMVC.Product;
import com.example.SpringMVC.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/shop")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

//    @GetMapping
//    @ResponseBody
//    private String productList () {
//        return "Hello World!";
//    }
    @GetMapping
    public String getProducts(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "shop";
    }

    @GetMapping("/add-product")
    public String getNewProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "add-product";
    }

    @PostMapping("/add-product")
    public String greetingSubmit(@ModelAttribute Product product) {
        productService.addProduct(product);
        return "redirect:/shop";
    }
}
