package com.linktic.productos.controller;

import com.linktic.productos.dto.ProductRequest;
import com.linktic.productos.dto.ProductResponse;
import com.linktic.productos.service.ProductService;
import com.linktic.productos.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@Import(ProductControllerTest.MockConfig.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public ProductService productService() {
            return Mockito.mock(ProductServiceImpl.class);
        }
    }

    @Test
    void shouldReturnAllProductsJsonApiFormat() throws Exception {
        ProductResponse product = new ProductResponse(1L, "Laptop", "Gamer", 1200.0);

        when(productService.getAll()).thenReturn(List.of(product));

        mockMvc.perform(get("/api/products")
                .header("X-API-KEY", "linktic2025*_"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value("1"))
                .andExpect(jsonPath("$.data[0].type").value("product"))
                .andExpect(jsonPath("$.data[0].attributes.name").value("Laptop"));
    }

    @Test
    void shouldCreateProductSuccessfully() throws Exception {
        ProductRequest request = new ProductRequest("Laptop", "Gamer", 1200.0);
        ProductResponse response = new ProductResponse(1L, "Laptop", "Gamer", 1200.0);

        when(productService.create(any(ProductRequest.class))).thenReturn(response);

        String json = """
                {
                  "name": "Laptop",
                  "price": 1200.0,
                  "description": "Gamer"
                }
                """;

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json).header("X-API-KEY", "linktic2025*_"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value("1"))
                .andExpect(jsonPath("$.data.attributes.name").value("Laptop"));
    }
}
