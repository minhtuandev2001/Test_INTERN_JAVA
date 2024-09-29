package com.minhtuan.internjava.service;

import com.minhtuan.internjava.dto.request.FilterProductRequest;
import com.minhtuan.internjava.dto.request.ProductRequest;
import com.minhtuan.internjava.dto.response.PageResponse;
import com.minhtuan.internjava.model.ProductEntity;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    ProductEntity createProduct(ProductRequest request);
    void deleteProductById(Long id);
    PageResponse<?> getAllProduct(int page, int pageSize);
    void updateProduct(Long id, ProductRequest request);
    ProductEntity getProductById(Long id);
    PageResponse<?> getAllProductByFilter(int page, int size, Long category_id,
            Long style_id, List<Long> colorIds, List<Long> sizeIds, BigDecimal minPrice, BigDecimal maxPrice);
}
