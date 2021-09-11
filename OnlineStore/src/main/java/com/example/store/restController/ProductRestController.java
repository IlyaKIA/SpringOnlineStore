package com.example.store.restController;

import com.example.store.domain.Product;
import com.example.store.dto.ProductDTO;
import com.example.store.error.ProductNotFoundException;
import com.example.store.error.ProductNotFoundException.*;
import com.example.store.service.ProductDtoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/shop/product")
@AllArgsConstructor
public class ProductRestController {
    private final ProductDtoService productDtoService;

    @GetMapping("/{id}")
    public ProductDTO getProductDtoById(@PathVariable Long id) {
        return productDtoService.findByIdDTO(id);
    }
    @GetMapping
    public Set<ProductDTO> getAllProducts(){
        return productDtoService.getAllProductsDTO();
    }

    @PostMapping
    public Product addProduct(@RequestBody ProductDTO productDto) {
        return productDtoService.saveProduct(productDto);
    }

    @PutMapping
    public Product updateProduct (@RequestBody ProductDTO productDto) {
        return productDtoService.saveProduct(productDto);
    }

    @DeleteMapping("/{id}")
    public int deleteProduct(@PathVariable Long id) {
        productDtoService.deleteProduct(id);
        return HttpStatus.OK.value();
    }

    @ExceptionHandler
    public ResponseEntity<ProductErrorResponse> handleException(ProductNotFoundException exc) {
        ProductErrorResponse productErrorResponse = new ProductErrorResponse();
        productErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        productErrorResponse.setMessage(exc.getMessage());
        productErrorResponse.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(productErrorResponse, HttpStatus.NOT_FOUND);
    }
}
