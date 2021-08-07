package org.example.context.repository;
import org.example.context.domain.Product;
import java.util.List;

public interface ProductRepository {
    List<Product> getProducts();
    Product getProduct(int id);
}
