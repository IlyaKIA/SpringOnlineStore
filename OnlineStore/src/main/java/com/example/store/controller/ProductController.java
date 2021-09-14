package com.example.store.controller;

import com.example.store.domain.Category;
import com.example.store.domain.Product;
import com.example.store.service.CategoryService;
import com.example.store.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import java.util.List;

@Controller
@RequestMapping("/shop")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public String getProducts(@RequestParam(value ="selectedCategory", required = false) String category,
                              @RequestParam(value ="minPrice", required = false) Integer minPrice,
                              @RequestParam(value ="maxPrice", required = false) Integer maxPrice,
                              @RequestParam(value ="search", required = false) String charSet,
                              Model model) {
        List<Product> products;
        if(category == null && minPrice == null && charSet == null)
            products = productService.findAllProducts();
        else if(charSet != null) products = productService.getProductsByCharSet(charSet);
        else products = productService.getProductsFiltered(category, minPrice, maxPrice);

        List<Category> categoryList = categoryService.findAllCategory();
        model.addAttribute("minPrice", productService.getMinPrice());
        model.addAttribute("maxPrice", productService.getMaxPrice());
        model.addAttribute("products", products);
        model.addAttribute("categoryList", categoryList);
        return "online_store";
    }

    @GetMapping("/add-product")
    public String getNewProductForm(Model model) {
        Product product = new Product();
        List<Category> categoryList = categoryService.findAllCategory();
        model.addAttribute("product", product);
        model.addAttribute("categoryList", categoryList);
        return "add-product";
    }

    @PostMapping("/add-product")
    public RedirectView saveNewProduct(@ModelAttribute Product product,
                                       @RequestParam(required = false) MultipartFile image,
                                       RedirectAttributes attributes) {

        productService.saveProductWithPicture(product, image);
        return new RedirectView ("/shop");
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam(value = "id", required = false) Long id) {
        productService.deleteProduct(id);
        return "redirect:/shop";
    }
}
