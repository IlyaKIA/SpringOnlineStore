package com.example.SpringMVC.controller;

import com.example.SpringMVC.domain.Category;
import com.example.SpringMVC.domain.Product;
import com.example.SpringMVC.repository.ProductDAO;
import com.example.SpringMVC.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/shop")
@AllArgsConstructor
public class ProductController {
    private final ProductDAO productDAO;

    @GetMapping
    public String getProducts(@RequestParam(value ="category", required = false) String category, Model model) {
        List<Product> products = productDAO.findAll();
        if(category == null){
            model.addAttribute("products", products);
            return "shop";
        }
        List<Category> categories = productDAO.findAllCategory();
        Category categoryFilter = null;
        for (Category c : categories) {
            if(c.getTitle().equals(category)){
                categoryFilter = c;
            }
        }
        if(categoryFilter == null){
            model.addAttribute("products", products);
            return "shop";
        }
        List<Product> productsFilter = new ArrayList<>();
        for(Product p : products){
            if(p.getCategory().getId() == categoryFilter.getId()) {
                productsFilter.add(p);
            }
        }
        model.addAttribute("products", productsFilter);
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
