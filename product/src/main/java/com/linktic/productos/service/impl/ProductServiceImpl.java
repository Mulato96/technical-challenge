package com.linktic.productos.service.impl;

import com.linktic.productos.dto.ProductRequest;
import com.linktic.productos.dto.ProductResponse;
import com.linktic.productos.entity.Product;
import com.linktic.productos.repository.ProductRepository;
import com.linktic.productos.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository repository;

    @Override
    public ProductResponse create(ProductRequest request) {
        logger.info("Creando Producto...");
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();
        product = repository.save(product);
        logger.debug("Producto creado: {} IdProducto", product.getId());
        return toResponse(product);
    }

    @Override
    public ProductResponse getById(Long id) {
        logger.info("Consultando Producto por id: {}", id);
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public List<ProductResponse> getAll() {
        logger.info("Consultando todos los Productos...");
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
