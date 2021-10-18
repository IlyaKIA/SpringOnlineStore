package com.example.store.controller;

import com.example.store.domain.Category;
import com.example.store.domain.Product;
import com.example.store.domain.Review;
import com.example.store.domain.authentication.UserProfile;
import com.example.store.service.CategoryService;
import com.example.store.service.ProductService;
import com.example.store.service.ReviewsService;
import com.example.store.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/shop")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserProfileService userProfileService;
    private final ReviewsService reviewsService;

    @GetMapping
    public String getProducts(@RequestParam(value ="selectedCategory", required = false) String category,
                              @RequestParam(value ="minPrice", required = false) Integer minPrice,
                              @RequestParam(value ="maxPrice", required = false) Integer maxPrice,
                              @RequestParam(value ="search", required = false) String charSet,
                              @RequestParam(required = false) Integer pageNum,
                              Model model) {
        final int pageSize = 6;
        Pageable pageRequest = PageRequest.of(pageNum == null ? 0 : pageNum, pageSize);

        Page<Product> pageOfProducts;
        if(category == null && minPrice == null && charSet == null) {
            pageOfProducts = productService.findAllProducts(pageRequest);
            minPrice = productService.getMinPrice();
            maxPrice = productService.getMaxPrice();
        }
        else if(charSet != null) pageOfProducts = productService.getProductsByCharSet(charSet, pageRequest);
        else pageOfProducts = productService.getProductsFiltered(category, minPrice, maxPrice, pageRequest);
        List<Category> categoryList = categoryService.findAllCategory();

        int totalPages = pageOfProducts.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        //Authentication check and forwarding user info
        if (authCheck().isPresent()) model.addAttribute("userProfile", authCheck().orElse(new UserProfile()));

        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("pageOfProducts", pageOfProducts);
        model.addAttribute("categoryList", categoryList);
        return "online_store";
    }

    @GetMapping("/add-product")
    public String getNewProductForm(Model model) {
        Product product = new Product();
        List<Category> categoryList = categoryService.findAllCategory();

        //Authentication check and forwarding user info
        if (authCheck().isPresent()) model.addAttribute("userProfile", authCheck().orElse(new UserProfile()));
        model.addAttribute("product", product);
        model.addAttribute("categoryList", categoryList);
        return "add-or-edit-product";
    }

    @PostMapping("/add-or-edit-product")
    public RedirectView saveNewProduct(@ModelAttribute Product product,
                                       @RequestParam(required = false) MultipartFile image,
                                       RedirectAttributes attributes) {
        productService.saveProductWithPicture(product, image);
        return new RedirectView ("/shop");
    }

    @PostMapping("/edit-product")
    public String getEditProductForm(@RequestParam(value = "id") Long id,
                                     Model model) {
        List<Category> categoryList = categoryService.findAllCategory();
        model.addAttribute("userProfile", authCheck().orElse(new UserProfile()));
        model.addAttribute("product", productService.findById(id).orElse(new Product()));
        model.addAttribute("categoryList", categoryList);
        return "add-or-edit-product";
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam(value = "id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/shop";
    }

    @GetMapping("/{id}")
    public String getProductInfo(@PathVariable Long id, Model model){
        model.addAttribute("product", productService.findById(id).orElse(new Product()));
        model.addAttribute("productReviews", reviewsService.findByProductId(id));
        model.addAttribute("userProfile", authCheck().orElse(new UserProfile()));
        model.addAttribute("comment", new Review());
        return "product-info";
    }

    @PostMapping("/new-comment")
    public RedirectView saveNewReview(@ModelAttribute Review review) {
        reviewsService.saveReview(review);
        return new RedirectView ("/shop");
    }

    private Optional<UserProfile> authCheck (){
        return userProfileService.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
