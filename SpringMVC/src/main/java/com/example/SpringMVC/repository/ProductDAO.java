package com.example.SpringMVC.repository;

import com.example.SpringMVC.domain.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public interface ProductDAO {
//    public SessionFactory sessionFactory = null;
//
//    public void init();

    public void shutdown();

    Product findById(Long id);

    List<Product> findAll();

    void deleteById(Long id);

    Product saveOrUpdate(Product product);
}