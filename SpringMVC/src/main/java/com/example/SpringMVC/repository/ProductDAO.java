package com.example.SpringMVC.repository;

import com.example.SpringMVC.domain.Category;
import com.example.SpringMVC.domain.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public interface ProductDAO {

    public void shutdown();

    Product findById(Long id);

    List<Product> findAll();

    List<Category> findAllCategory();

    void deleteById(Long id);

    Product saveOrUpdate(Product product);

    List<Product> findProductsByCategory();
}