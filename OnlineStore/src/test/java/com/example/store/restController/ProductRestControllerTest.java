package com.example.store.restController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.store.dto.ProductDTO;
import com.example.store.error.ProductNotFoundException;
import com.example.store.service.ProductDtoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ProductRestController.class})
@ExtendWith(SpringExtension.class)
class ProductRestControllerTest {
    @MockBean
    private ProductDtoService productDtoService;

    @Autowired
    private ProductRestController productRestController;

    @Test
    void testAddProduct() throws Exception {
        when(this.productDtoService.getAllProductsDTO()).thenReturn(new HashSet<ProductDTO>());

        ProductDTO productDTO = new ProductDTO();
        productDTO.setPicturePath("Picture Path");
        productDTO.setId(123L);
        productDTO.setTitle("Dr");
        productDTO.setPrice(1);
        String content = (new ObjectMapper()).writeValueAsString(productDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/shop/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.productRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        doNothing().when(this.productDtoService).deleteProduct((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/shop/product/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.productRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("200"));
    }

    @Test
    void testHandleException() {
        ResponseEntity<ProductNotFoundException.ProductErrorResponse> actualHandleExceptionResult = this.productRestController
                .handleException(new ProductNotFoundException("An error occurred"));
        assertTrue(actualHandleExceptionResult.getHeaders().isEmpty());
        assertTrue(actualHandleExceptionResult.hasBody());
        assertEquals(HttpStatus.NOT_FOUND, actualHandleExceptionResult.getStatusCode());
        ProductNotFoundException.ProductErrorResponse body = actualHandleExceptionResult.getBody();
        assertEquals("An error occurred", body.getMessage());
        assertEquals(404, body.getStatus());
    }

    @Test
    void testHandleException2() {
        ProductNotFoundException productNotFoundException = new ProductNotFoundException("An error occurred");
        productNotFoundException.addSuppressed(new Throwable());
        ResponseEntity<ProductNotFoundException.ProductErrorResponse> actualHandleExceptionResult = this.productRestController
                .handleException(productNotFoundException);
        assertTrue(actualHandleExceptionResult.getHeaders().isEmpty());
        assertTrue(actualHandleExceptionResult.hasBody());
        assertEquals(HttpStatus.NOT_FOUND, actualHandleExceptionResult.getStatusCode());
        ProductNotFoundException.ProductErrorResponse body = actualHandleExceptionResult.getBody();
        assertEquals("An error occurred", body.getMessage());
        assertEquals(404, body.getStatus());
    }

    @Test
    void testHandleException3() {
        ProductNotFoundException productNotFoundException = mock(ProductNotFoundException.class);
        when(productNotFoundException.getMessage()).thenReturn("Not all who wander are lost");
        ResponseEntity<ProductNotFoundException.ProductErrorResponse> actualHandleExceptionResult = this.productRestController
                .handleException(productNotFoundException);
        assertTrue(actualHandleExceptionResult.getHeaders().isEmpty());
        assertTrue(actualHandleExceptionResult.hasBody());
        assertEquals(HttpStatus.NOT_FOUND, actualHandleExceptionResult.getStatusCode());
        ProductNotFoundException.ProductErrorResponse body = actualHandleExceptionResult.getBody();
        assertEquals("Not all who wander are lost", body.getMessage());
        assertEquals(404, body.getStatus());
        verify(productNotFoundException).getMessage();
        assertTrue(this.productRestController.getAllProducts().isEmpty());
    }

    @Test
    void testGetAllProducts() throws Exception {
        when(this.productDtoService.getAllProductsDTO()).thenReturn(new HashSet<ProductDTO>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/shop/product");
        MockMvcBuilders.standaloneSetup(this.productRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetProductDtoById() throws Exception {
        when(this.productDtoService.findByIdDTO(anyLong())).thenReturn(new ProductDTO());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/shop/product/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.productRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"id\":null,\"title\":null,\"price\":null,\"picturePath\":null}"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        when(this.productDtoService.getAllProductsDTO()).thenReturn(new HashSet<ProductDTO>());

        ProductDTO productDTO = new ProductDTO();
        productDTO.setPicturePath("Picture Path");
        productDTO.setId(123L);
        productDTO.setTitle("Dr");
        productDTO.setPrice(1);
        String content = (new ObjectMapper()).writeValueAsString(productDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/shop/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.productRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

