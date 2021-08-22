package com.example.SpringMVC.repository.impl;

import com.example.SpringMVC.domain.Category;
import com.example.SpringMVC.domain.Product;
import com.example.SpringMVC.repository.ProductDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDAO {
    public static SessionFactory factory;

    @PostConstruct
    public void init(){
        factory = new Configuration()
                .configure("config/hibernate.cfg.xml")
                .buildSessionFactory();

//        Product product = saveOrUpdate(new  Product("Toaster", 750, "Electronics"));

//        System.out.println(findById(2L));

//        System.out.println(findAll());

//        deleteById(1L);
//        System.out.println(findById(2L));

    }

    @Override
    public void shutdown(){
        factory.close();
    }

    @Override
    public Product findById(Long id){
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            Product product = session.get(Product.class, id);
            session.getTransaction().commit();
            return product;
        }
    }

    @Override
    public List<Product> findAll(){
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            List<Product> resultList = session.createQuery("select s from Product s", Product.class)
                    .getResultList();
            session.getTransaction().commit();
            return resultList;
        }
    }

    @Override
    public List<Category> findAllCategory() {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            List<Category> resultList = session.createQuery("select s from Category s", Category.class)
                    .getResultList();
            session.getTransaction().commit();
            return resultList;
        }
    }

    @Override
    public void deleteById(Long id){
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            Product product = session.get(Product.class, id);
            session.delete(product);
            session.getTransaction().commit();
        }
    }

    @Override
    public Product saveOrUpdate(Product product){
        Category thisCategory = null;
        try(Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            List<Category> resultList = session.createQuery("select s from Category s", Category.class)
                    .getResultList();
            String categoryTitle = product.getCategory().getTitle();
            for (Category p : resultList) {
                if (categoryTitle.equals(p.getTitle())) {
                    thisCategory = p;
                }
            }
            if (thisCategory != null) {
                product.setCategory(thisCategory);
            } else {
                thisCategory = new Category(product.getCategory().getTitle());
                session.save(thisCategory);
            }
            session.getTransaction().commit();
        }
        try(Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            product.setCategory(thisCategory);
            session.saveOrUpdate(product);
            session.getTransaction().commit();
        }
        return product;
    }

    @Override
    public List<Product> findProductsByCategory() {
        return null;
    }
}
