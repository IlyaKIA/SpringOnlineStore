package com.example.store.service.impl;

import com.example.store.domain.Category;
import com.example.store.domain.Product;
import com.example.store.repository.CategoryRepository;
import com.example.store.repository.ProductRepository;
import com.example.store.service.CategoryService;
import com.example.store.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ProductServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ProductServiceImplTest {
    @MockBean
    private CategoryService categoryService;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Test
    void testConstructor() {
        ProductRepository productRepository = mock(ProductRepository.class);
        CategoryServiceImpl categoryServiceImpl = new CategoryServiceImpl(mock(CategoryRepository.class));
        new ProductServiceImpl(productRepository, categoryServiceImpl);

        assertTrue(categoryServiceImpl.findAllCategory().isEmpty());
    }

    @Test
    void testFindAllProducts() {
        PageImpl<Product> pageImpl = new PageImpl<Product>(new ArrayList<Product>());
        when(this.productRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
        Page<Product> actualFindAllProductsResult = this.productService.findAllProducts(null);
        assertSame(pageImpl, actualFindAllProductsResult);
        assertTrue(actualFindAllProductsResult.toList().isEmpty());
        verify(this.productRepository).findAll((org.springframework.data.domain.Pageable) any());
    }

    @Test
    void testFindById() {
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
        Optional<Product> ofResult = Optional.<Product>of(product);
        when(this.productRepository.findById((Long) any())).thenReturn(ofResult);
        Optional<Product> actualFindByIdResult = this.productService.findById(123L);
        assertSame(ofResult, actualFindByIdResult);
        assertTrue(actualFindByIdResult.isPresent());
        verify(this.productRepository).findById((Long) any());
    }

    @Test
    void testAddProduct() {
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
        when(this.productRepository.save((Product) any())).thenReturn(product);

        Category category1 = new Category();
        category1.setId(123L);
        category1.setProducts(new ArrayList<Product>());
        category1.setTitle("Dr");

        Product product1 = new Product();
        product1.setPicturePath("Picture Path");
        product1.setId(123L);
        product1.setTitle("Dr");
        product1.setPrice(1);
        product1.setCategory(category1);
        assertSame(product, this.productService.addProduct(product1));
        verify(this.productRepository).save((Product) any());
    }

    @Test
    void testGetProductsFromCategory() {
        when(this.productRepository.findAll((org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<Product>(new ArrayList<Product>()));
        when(this.categoryService.findAllCategory()).thenReturn(new ArrayList<Category>());
        assertNull(this.productService.getProductsFromCategory("Category", null));
        verify(this.productRepository).findAll((org.springframework.data.domain.Pageable) any());
        verify(this.categoryService).findAllCategory();
    }

    @Test
    void testGetProductsFromCategory2() {
        when(this.productRepository.findAll((org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<Product>(new ArrayList<Product>()));

        Category category = new Category();
        category.setId(123L);
        category.setProducts(new ArrayList<Product>());
        category.setTitle("Dr");

        ArrayList<Category> categoryList = new ArrayList<Category>();
        categoryList.add(category);
        when(this.categoryService.findAllCategory()).thenReturn(categoryList);
        assertNull(this.productService.getProductsFromCategory("Category", null));
        verify(this.productRepository).findAll((org.springframework.data.domain.Pageable) any());
        verify(this.categoryService).findAllCategory();
    }

    @Test
    void testGetProductsFromCategory3() {
        when(this.productRepository.findAll((org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<Product>(new ArrayList<Product>()));

        Category category = new Category();
        category.setId(123L);
        category.setProducts(new ArrayList<Product>());
        category.setTitle("Dr");

        ArrayList<Category> categoryList = new ArrayList<Category>();
        categoryList.add(category);
        when(this.categoryService.findAllCategory()).thenReturn(categoryList);
        assertNull(this.productService.getProductsFromCategory(null, null));
        verify(this.productRepository).findAll((org.springframework.data.domain.Pageable) any());
        verify(this.categoryService).findAllCategory();
    }

    @Test
    void testGetProductsFromCategory4() {
        when(this.productRepository.findAll((org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<Product>(new ArrayList<Product>()));

        Category category = new Category();
        category.setId(123L);
        category.setProducts(new ArrayList<Product>());
        category.setTitle("Title");

        ArrayList<Category> categoryList = new ArrayList<Category>();
        categoryList.add(category);
        when(this.categoryService.findAllCategory()).thenReturn(categoryList);
        assertNull(this.productService.getProductsFromCategory("Dr", null));
        verify(this.productRepository).findAll((org.springframework.data.domain.Pageable) any());
        verify(this.categoryService).findAllCategory();
    }

    @Test
    void testGetProductsFromCategory5() {
        Category category = new Category();
        category.setId(123L);
        category.setProducts(new ArrayList<Product>());
        category.setTitle("Dr");

        Product product = new Product();
        product.setPicturePath("Dr");
        product.setId(123L);
        product.setTitle("Dr");
        product.setPrice(0);
        product.setCategory(category);

        ArrayList<Product> productList = new ArrayList<Product>();
        productList.add(product);
        PageImpl<Product> pageImpl = new PageImpl<Product>(productList);
        when(this.productRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);

        Category category1 = new Category();
        category1.setId(123L);
        category1.setProducts(new ArrayList<Product>());
        category1.setTitle("Title");

        ArrayList<Category> categoryList = new ArrayList<Category>();
        categoryList.add(category1);
        when(this.categoryService.findAllCategory()).thenReturn(categoryList);
        assertNull(this.productService.getProductsFromCategory("Dr", null));
        verify(this.productRepository).findAll((org.springframework.data.domain.Pageable) any());
        verify(this.categoryService).findAllCategory();
    }

    @Test
    void testGetProductsFiltered() {
        PageImpl<Product> pageImpl = new PageImpl<Product>(new ArrayList<Product>());
        when(this.productRepository.findByCategory_TitleEqualsAndPriceGreaterThanEqualAndPriceLessThanEqual((String) any(),
                (Integer) any(), (Integer) any(), (org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
        Page<Product> actualProductsFiltered = this.productService.getProductsFiltered("Category", 1, 3, null);
        assertSame(pageImpl, actualProductsFiltered);
        assertTrue(actualProductsFiltered.toList().isEmpty());
        verify(this.productRepository).findByCategory_TitleEqualsAndPriceGreaterThanEqualAndPriceLessThanEqual(
                (String) any(), (Integer) any(), (Integer) any(), (org.springframework.data.domain.Pageable) any());
    }

    @Test
    void testGetProductsFiltered2() {
        PageImpl<Product> pageImpl = new PageImpl<Product>(new ArrayList<Product>());
        when(this.productRepository.findByPriceGreaterThanEqualAndPriceLessThanEqual((Integer) any(), (Integer) any(),
                (org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
        when(this.productRepository.findByCategory_TitleEqualsAndPriceGreaterThanEqualAndPriceLessThanEqual((String) any(),
                (Integer) any(), (Integer) any(), (org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<Product>(new ArrayList<Product>()));
        Page<Product> actualProductsFiltered = this.productService.getProductsFiltered("All", 1, 3, null);
        assertSame(pageImpl, actualProductsFiltered);
        assertTrue(actualProductsFiltered.toList().isEmpty());
        verify(this.productRepository).findByPriceGreaterThanEqualAndPriceLessThanEqual((Integer) any(), (Integer) any(),
                (org.springframework.data.domain.Pageable) any());
    }

    @Test
    void testGetProductsFiltered3() {
        when(this.productRepository.findByPriceGreaterThanEqualAndPriceLessThanEqual((Integer) any(), (Integer) any(),
                (org.springframework.data.domain.Pageable) any())).thenReturn(new PageImpl<Product>(new ArrayList<Product>()));
        when(this.productRepository.findByCategory_TitleEqualsAndPriceGreaterThanEqualAndPriceLessThanEqual((String) any(),
                (Integer) any(), (Integer) any(), (org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<Product>(new ArrayList<Product>()));
        assertNull(this.productService.getProductsFiltered(null, 1, 3, null));
    }

    @Test
    void testGetProductsFiltered4() {
        PageImpl<Product> pageImpl = new PageImpl<Product>(new ArrayList<Product>());
        when(this.productRepository.findByCategory_TitleEquals((String) any(),
                (org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
        when(this.productRepository.findByPriceGreaterThanEqualAndPriceLessThanEqual((Integer) any(), (Integer) any(),
                (org.springframework.data.domain.Pageable) any())).thenReturn(new PageImpl<Product>(new ArrayList<Product>()));
        when(this.productRepository.findByCategory_TitleEqualsAndPriceGreaterThanEqualAndPriceLessThanEqual((String) any(),
                (Integer) any(), (Integer) any(), (org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<Product>(new ArrayList<Product>()));
        Page<Product> actualProductsFiltered = this.productService.getProductsFiltered("All", null, 3, null);
        assertSame(pageImpl, actualProductsFiltered);
        assertTrue(actualProductsFiltered.toList().isEmpty());
        verify(this.productRepository).findByCategory_TitleEquals((String) any(),
                (org.springframework.data.domain.Pageable) any());
    }

    @Test
    void testGetProductsFiltered5() {
        when(this.productRepository.findByCategory_TitleEquals((String) any(),
                (org.springframework.data.domain.Pageable) any())).thenReturn(new PageImpl<Product>(new ArrayList<Product>()));
        when(this.productRepository.findByPriceGreaterThanEqualAndPriceLessThanEqual((Integer) any(), (Integer) any(),
                (org.springframework.data.domain.Pageable) any())).thenReturn(new PageImpl<Product>(new ArrayList<Product>()));
        when(this.productRepository.findByCategory_TitleEqualsAndPriceGreaterThanEqualAndPriceLessThanEqual((String) any(),
                (Integer) any(), (Integer) any(), (org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<Product>(new ArrayList<Product>()));
        assertNull(this.productService.getProductsFiltered(null, null, 3, null));
    }

    @Test
    void testGetMinPrice() {
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
        when(this.productRepository.findFirstByOrderByPrice()).thenReturn(product);
        assertEquals(1, this.productService.getMinPrice().intValue());
        verify(this.productRepository).findFirstByOrderByPrice();
    }

    @Test
    void testGetMaxPrice() {
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
        when(this.productRepository.findFirstByOrderByPriceDesc()).thenReturn(product);
        assertEquals(1, this.productService.getMaxPrice().intValue());
        verify(this.productRepository).findFirstByOrderByPriceDesc();
    }

    @Test
    void testGetProductsByCharSet() {
        PageImpl<Product> pageImpl = new PageImpl<Product>(new ArrayList<Product>());
        when(this.productRepository.findByTitleLikeIgnoreCase((String) any(),
                (org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
        Page<Product> actualProductsByCharSet = this.productService.getProductsByCharSet("Char Set", null);
        assertSame(pageImpl, actualProductsByCharSet);
        assertTrue(actualProductsByCharSet.toList().isEmpty());
        verify(this.productRepository).findByTitleLikeIgnoreCase((String) any(),
                (org.springframework.data.domain.Pageable) any());
    }

    @Test
    void testDeleteProduct() {
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
        Optional<Product> ofResult = Optional.<Product>of(product);
        doNothing().when(this.productRepository).delete((Product) any());
        when(this.productRepository.findById((Long) any())).thenReturn(ofResult);
        this.productService.deleteProduct(123L);
        verify(this.productRepository).delete((Product) any());
        verify(this.productRepository).findById((Long) any());
    }

    @Test
    void testSaveProductWithPicture() throws IOException {
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
        ProductRepository productRepository = mock(ProductRepository.class);
        when(productRepository.save((Product) any())).thenReturn(product);
        ProductServiceImpl productServiceImpl = new ProductServiceImpl(productRepository,
                new CategoryServiceImpl(mock(CategoryRepository.class)));

        Category category1 = new Category();
        category1.setId(123L);
        category1.setProducts(new ArrayList<Product>());
        category1.setTitle("Dr");

        Product product1 = new Product();
        product1.setPicturePath("Picture Path");
        product1.setId(123L);
        product1.setTitle("Dr");
        product1.setPrice(1);
        product1.setCategory(category1);
        assertSame(product, productServiceImpl.saveProductWithPicture(product1,
                new MockMultipartFile("Name", new ByteArrayInputStream(new byte[]{}))));
        verify(productRepository).save((Product) any());
    }

    @Test
    void testSaveProductWithPicture2() {
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
        ProductRepository productRepository = mock(ProductRepository.class);
        when(productRepository.save((Product) any())).thenReturn(product);
        ProductServiceImpl productServiceImpl = new ProductServiceImpl(productRepository,
                new CategoryServiceImpl(mock(CategoryRepository.class)));

        Category category1 = new Category();
        category1.setId(123L);
        category1.setProducts(new ArrayList<Product>());
        category1.setTitle("Dr");

        Product product1 = new Product();
        product1.setPicturePath("Picture Path");
        product1.setId(123L);
        product1.setTitle("Dr");
        product1.setPrice(1);
        product1.setCategory(category1);
        assertSame(product, productServiceImpl.saveProductWithPicture(product1, null));
        verify(productRepository).save((Product) any());
    }

    @Test
    void testSaveProductWithPicture3() {
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
        ProductRepository productRepository = mock(ProductRepository.class);
        when(productRepository.save((Product) any())).thenReturn(product);
        ProductServiceImpl productServiceImpl = new ProductServiceImpl(productRepository,
                new CategoryServiceImpl(mock(CategoryRepository.class)));

        Category category1 = new Category();
        category1.setId(123L);
        category1.setProducts(new ArrayList<Product>());
        category1.setTitle("Dr");

        Product product1 = new Product();
        product1.setPicturePath("Picture Path");
        product1.setId(123L);
        product1.setTitle("Dr");
        product1.setPrice(1);
        product1.setCategory(category1);
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.isEmpty()).thenReturn(true);
        assertSame(product, productServiceImpl.saveProductWithPicture(product1, multipartFile));
        verify(productRepository).save((Product) any());
        verify(multipartFile).isEmpty();
    }

    @Test
    void testSaveProduct() {
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
        when(this.productRepository.save((Product) any())).thenReturn(product);

        Category category1 = new Category();
        category1.setId(123L);
        category1.setProducts(new ArrayList<Product>());
        category1.setTitle("Dr");

        Product product1 = new Product();
        product1.setPicturePath("Picture Path");
        product1.setId(123L);
        product1.setTitle("Dr");
        product1.setPrice(1);
        product1.setCategory(category1);
        assertSame(product, this.productService.saveProduct(product1));
        verify(this.productRepository).save((Product) any());
    }
}

