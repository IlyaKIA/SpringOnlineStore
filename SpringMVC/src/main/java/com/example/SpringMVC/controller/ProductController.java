package com.example.SpringMVC.controller;

import com.example.SpringMVC.domain.Product;
import com.example.SpringMVC.repository.ProductDAO;
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
//    private final ProductService productService;
    private final ProductDAO productDAO;

//    @GetMapping
//    @ResponseBody
//    private String productList () {
//        return "Hello World!";
//    }
    @GetMapping
    public String getProducts(Model model) {
        //TODO
        List<Product> products = productDAO.findAll();
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
        productDAO.saveOrUpdate(product);
        return "redirect:/shop";
    }
}
