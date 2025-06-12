package com.linktic.productos.controller;

import com.linktic.productos.api.JsonApiResponse;
import com.linktic.productos.api.JsonApiUtils;
import com.linktic.productos.dto.ProductRequest;
import com.linktic.productos.dto.ProductResponse;
import com.linktic.productos.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<JsonApiResponse> create(@RequestBody  @Valid ProductRequest request) {
        ProductResponse response = productService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(JsonApiUtils.buildResponse("product", String.valueOf(response.getId()), response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JsonApiResponse> getById(@PathVariable Long id) {
        ProductResponse response = productService.getById(id);
        return ResponseEntity.ok(JsonApiUtils.buildResponse("product", String.valueOf(response.getId()), response));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        List<ProductResponse> products = productService.getAll();

        List<Map<String, Object>> data = products.stream()
                .map(p -> JsonApiUtils.buildMap("product", String.valueOf(p.getId()), p))
                .toList();

        return ResponseEntity.ok(Map.of("data", data));
    }
}

