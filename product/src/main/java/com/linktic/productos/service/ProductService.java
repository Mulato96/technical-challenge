package com.linktic.productos.service;

import com.linktic.productos.dto.ProductRequest;
import com.linktic.productos.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest request);
    ProductResponse getById(Long id);
    List<ProductResponse> getAll();
}