package com.nhom6.microservices.product_service.service;

import com.nhom6.microservices.product_service.dto.ProductRequest;
import com.nhom6.microservices.product_service.dto.ProductRespone;
import com.nhom6.microservices.product_service.model.Product;
import com.nhom6.microservices.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public ProductRespone createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .skuCode(productRequest.skuCode())
                .price(productRequest.price())
                .type(productRequest.type())
                .imageUrl(productRequest.imageUrl())
                .build();
        productRepository.save(product);
        log.info("Product created successfully");
        return new ProductRespone(product.getId(), product.getName(), product.getDescription(),
                product.getSkuCode(), product.getPrice(), product.getType(), product.getImageUrl());
    }

    public List<ProductRespone> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductRespone(product.getId(), product.getName(), product.getDescription(),
                        product.getSkuCode(),
                        product.getPrice(), product.getType(), product.getImageUrl()))
                .toList();
    }
    public void deleteAllProducts() {
        productRepository.deleteAll();
        log.info("All products deleted successfully");
    }

    public ProductRespone getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return new ProductRespone(product.getId(), product.getName(), product.getDescription(),
                product.getSkuCode(), product.getPrice(), product.getType(), product.getImageUrl());
    }

    public ProductRespone updateProduct(String id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(productRequest.name());
        product.setDescription(productRequest.description());
        product.setSkuCode(productRequest.skuCode());
        product.setPrice(productRequest.price());
        product.setType(productRequest.type());
        product.setImageUrl(productRequest.imageUrl()); 
        productRepository.save(product);
        log.info("Product updated successfully");
        return new ProductRespone(product.getId(), product.getName(), product.getDescription(),
                product.getSkuCode(), product.getPrice(), product.getType(), product.getImageUrl());
    }
    public void deleteProduct(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
        log.info("Product deleted successfully");
    }
    public List<ProductRespone> getProductByType(String type) {
         return  productRepository.findByType(type).stream()
                .map(product -> new ProductRespone(product.getId(), product.getName(), product.getDescription()
                        ,product.getSkuCode(),product.getPrice(), product.getType(), product.getImageUrl()))
                .toList();
    }

}       

