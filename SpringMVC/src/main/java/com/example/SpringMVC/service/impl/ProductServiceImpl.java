package com.example.SpringMVC.service.impl;

import com.example.SpringMVC.domain.Product;
import com.example.SpringMVC.repository.ProductDAO;
import com.example.SpringMVC.service.ProductService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;

    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public List<Product> findAll() {
        return productDAO.findAll();
    }

    @Override
    public Product findById(long id) {
        return productDAO.findById(id);
    }

    @Override
    public Product addProduct(Product product) {
        return productDAO.saveOrUpdate(product);
    }

}
