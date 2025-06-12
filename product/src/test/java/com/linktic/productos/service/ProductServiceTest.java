package com.linktic.productos.service;


import com.linktic.productos.dto.ProductRequest;
import com.linktic.productos.dto.ProductResponse;
import com.linktic.productos.entity.Product;
import com.linktic.productos.repository.ProductRepository;
import com.linktic.productos.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void shouldCreateProductSuccessfully() {
        ProductRequest request = new ProductRequest("Laptop", "Gamer",1000.0);
        Product product = new Product(1L,"Laptop", "Gamer" ,1000.0);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse response = productService.create(request);

        assertEquals("Laptop", response.getName());
        assertEquals(1000.0, response.getPrice());
        assertEquals(1L, response.getId());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldReturnListOfProducts() {
        Product product1 = new Product(1L,"Laptop", "Gamer",1000.0);
        product1.setId(1L);
        Product product2 = new Product(2L,"Monitor", "24 pulgadas",300.0);
        product2.setId(2L);

        when(productRepository.findAll()).thenReturn(List.of(product1, product2));

        List<ProductResponse> responses = productService.getAll();

        assertEquals(2, responses.size());
        assertEquals("Laptop", responses.get(0).getName());
    }

}
