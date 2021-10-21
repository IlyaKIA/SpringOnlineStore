package com.example.store.convertor;

import com.example.store.domain.Category;
import com.example.store.domain.Product;
import com.example.store.dto.ProductDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProductConvertorTest {
    @Test
    void testConvertToDTO() {
        Category category = new Category();
        category.setId(123L);
        category.setProducts(new ArrayList<Product>());
        category.setTitle("Dr");

        Product product = new Product();
        product.setPicturePath("Picture Path");
        product.setId(123L);
        product.setTitle("Dr");
        product.setPrice(1);
        product.setCategory(category);
        ProductDTO actualConvertToDTOResult = ProductConvertor.convertToDTO(product);
        assertEquals(123L, actualConvertToDTOResult.getId().longValue());
        assertEquals("Dr", actualConvertToDTOResult.getTitle());
        assertEquals(1, actualConvertToDTOResult.getPrice().intValue());
        assertEquals("Picture Path", actualConvertToDTOResult.getPicturePath());
    }

    @Test
    void testGetAllProductsDTO() {
        assertTrue(ProductConvertor.getAllProductsDTO(new ArrayList<Product>()).isEmpty());
    }

    @Test
    void testGetAllProductsDTO2() {
        Category category = new Category();
        category.setId(123L);
        category.setProducts(new ArrayList<Product>());
        category.setTitle("Dr");

        Product product = new Product();
        product.setPicturePath("Picture Path");
        product.setId(123L);
        product.setTitle("Dr");
        product.setPrice(0);
        product.setCategory(category);

        ArrayList<Product> productList = new ArrayList<Product>();
        productList.add(product);
        assertEquals(1, ProductConvertor.getAllProductsDTO(productList).size());
    }

    @Test
    void testGetAllProductsDTO3() {
        Category category = new Category();
        category.setId(123L);
        category.setProducts(new ArrayList<Product>());
        category.setTitle("Dr");

        Product product = new Product();
        product.setPicturePath("Picture Path");
        product.setId(123L);
        product.setTitle("Dr");
        product.setPrice(0);
        product.setCategory(category);

        Category category1 = new Category();
        category1.setId(123L);
        category1.setProducts(new ArrayList<Product>());
        category1.setTitle("Dr");

        Product product1 = new Product();
        product1.setPicturePath("Picture Path");
        product1.setId(123L);
        product1.setTitle("Dr");
        product1.setPrice(0);
        product1.setCategory(category1);

        ArrayList<Product> productList = new ArrayList<Product>();
        productList.add(product1);
        productList.add(product);
        assertEquals(1, ProductConvertor.getAllProductsDTO(productList).size());
    }

    @Test
    void testConvertToEntity() {
        Product actualConvertToEntityResult = ProductConvertor.convertToEntity(new ProductDTO());
        assertNull(actualConvertToEntityResult.getCategory());
        assertNull(actualConvertToEntityResult.getTitle());
        assertNull(actualConvertToEntityResult.getPrice());
        assertNull(actualConvertToEntityResult.getPicturePath());
        assertNull(actualConvertToEntityResult.getId());
    }

    @Test
    void testConvertToEntity2() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPicturePath("com.example.store.dto.ProductDTO");
        Product actualConvertToEntityResult = ProductConvertor.convertToEntity(productDTO);
        assertNull(actualConvertToEntityResult.getCategory());
        assertNull(actualConvertToEntityResult.getTitle());
        assertNull(actualConvertToEntityResult.getPrice());
        assertEquals("com.example.store.dto.ProductDTO", actualConvertToEntityResult.getPicturePath());
        assertNull(actualConvertToEntityResult.getId());
    }
}

