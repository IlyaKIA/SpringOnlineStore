package com.example.springdata_hw7.controller;

import com.example.springdata_hw7.domain.Category;
import com.example.springdata_hw7.domain.Product;
import com.example.springdata_hw7.repository.CategoryRepository;
import com.example.springdata_hw7.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Controller
@RequestMapping("/shop")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public String getProducts(@RequestParam(value ="category", required = false) String category,
                              @RequestParam(value ="minPrise", required = false) Integer minPrise,
                              @RequestParam(value ="maxPrise", required = false) Integer maxPrise,
                              @RequestParam(value ="symbolsSet", required = false) String symbolsSet, Model model) {
        List<Product> products;
        if(category == null && minPrise == null){
            products = productService.findAllProducts();
        } else {
            products = productService.getProductsFiltered(category, minPrise, maxPrise, symbolsSet);

//            products = productService.getProductsFromCategory(category);
//            if (products == null) {
//                products = productService.findAllProducts();
//            }
        }
        model.addAttribute("minPrise", productService.getMinPrise());
        model.addAttribute("maxPrise", productService.getMaxPrise());
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
