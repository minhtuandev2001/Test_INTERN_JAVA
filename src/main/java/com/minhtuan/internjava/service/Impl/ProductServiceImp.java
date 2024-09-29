package com.minhtuan.internjava.service.Impl;

import com.minhtuan.internjava.dto.request.FilterProductRequest;
import com.minhtuan.internjava.dto.request.ProductRequest;
import com.minhtuan.internjava.dto.response.PageResponse;
import com.minhtuan.internjava.dto.response.ProductResponse;
import com.minhtuan.internjava.exception.AppException;
import com.minhtuan.internjava.model.*;
import com.minhtuan.internjava.repository.*;
import com.minhtuan.internjava.repository.custom.FilterProductsRepository;
import com.minhtuan.internjava.service.ProductService;
import com.minhtuan.internjava.utils.toSlug;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ProductServiceImp implements ProductService {

    ProductRepository productRepository;
    ColorRepository colorRepository;
    StyleRepository styleRepository;
    SizeRepository sizeRepository;
    CategoryRepository categoryRepository;
    FilterProductsRepository filterProductsRepository;

    @Override
    public ProductEntity createProduct(ProductRequest request) {

        ProductEntity product = ProductEntity.builder()
                .name(request.getName())
                .images(String.join(",", request.getImages()))
                .description(request.getDescription())
                .stock(request.getStock())
                .price(request.getPrice())
                .slug(toSlug.nameToSlug(request.getName()))
                .thumbnail(request.getThumbnail())
                .build();
        for (Long i : request.getColorIds()) {
            ColorEntity color = colorRepository.findById(i)
                    .orElseThrow(() -> new AppException(404, "color not found"));
            product.addColor(color);
        }
        for (Long i : request.getSizeIds()) {
            SizeEntity size = sizeRepository.findById(i)
                    .orElseThrow(() -> new AppException(404, "size not found"));
            product.addSize(size);
        }
        CategoryEntity category = categoryRepository.findById(request.getCategory_id())
                .orElseThrow(() -> new AppException(404, "category not found"));
        product.setCategory(category);
        StyleEntity styleEntity = styleRepository.findById(request.getStyle_id())
                .orElseThrow(() -> new AppException(404, "style not found"));
        product.setStyle(styleEntity);

        return productRepository.save(product);
    }

    @Override
    public void deleteProductById(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(404, "product not found"));
        productRepository.deleteById(id);
    }

    @Override
    public PageResponse<?> getAllProduct(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<ProductEntity> products = productRepository.findAll(pageable);
        Long count = productRepository.count();
        System.out.println(products.stream().count());
        // trung chuyển dữ liệu
        List<ProductResponse> productResponses = products.stream()
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .discount(product.getDiscount())
                        .price(product.getPrice())
                        .rating(product.getRating())
                        .thumbnail(product.getThumbnail())
                        .build()).toList();

        return PageResponse.builder()
                .page(page)
                .pageSize(pageSize)
                .totalPage(products.getTotalPages())
                .items(productResponses)
                .totalElement(count)
                .build();
    }

    @Override
    public void updateProduct(Long id, ProductRequest request) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(404, "product not found"));
        // thay đổi category
        ProductEntity productNew = ProductEntity.builder()
                .id(id)
                .name(request.getName() == null ? product.getName() : request.getName())
                .images(request.getImages() == null ? product.getImages() : String.join(",", request.getImages()))
                .description(request.getDescription() == null ? product.getDescription() : request.getDescription())
                .stock(request.getStock() == null ? product.getStock() : request.getStock())
                .price(request.getPrice() == null ? product.getPrice() : request.getPrice())
                .slug(request.getName() == null ? product.getSlug() : toSlug.nameToSlug(request.getName()))
                .thumbnail(request.getThumbnail() == null ? product.getThumbnail() : request.getThumbnail())
                .build();
        if(request.getColorIds() != null){
            for (Long i : request.getColorIds()) {
                ColorEntity color = colorRepository.findById(i)
                        .orElseThrow(() -> new AppException(404, "color not found"));
                productNew.addColor(color);
            }
        }else{
            productNew.setColors(product.getColors());
        }
        if(request.getSizeIds() != null){
            for (Long i : request.getSizeIds()) {
                SizeEntity size = sizeRepository.findById(i)
                        .orElseThrow(() -> new AppException(404, "size not found"));
                productNew.addSize(size);
            }
        }else{
            productNew.setSizes(product.getSizes());
        }
        if(request.getCategory_id() != null){
            CategoryEntity category = categoryRepository.findById(request.getCategory_id())
                    .orElseThrow(() -> new AppException(404, "category not found"));
            productNew.setCategory(category);
        }else{
            productNew.setCategory(product.getCategory());
        }
        if(request.getStyle_id() != null){
            StyleEntity styleEntity = styleRepository.findById(request.getStyle_id())
                    .orElseThrow(() -> new AppException(404, "style not found"));
            productNew.setStyle(styleEntity);
        }else{
            productNew.setStyle(product.getStyle());
        }
        productRepository.save(productNew);
    }

    @Override
    public ProductEntity getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new AppException(404, "product not found"));
    }

    @Override
    public PageResponse<?> getAllProductByFilter(int page, int size, Long category_id, Long style_id,
                                                       List<Long> colorIds, List<Long> sizeIds, BigDecimal minPrice,
                                                       BigDecimal maxPrice) {
        return filterProductsRepository.getAllProductByFilter(page, size, category_id, style_id, colorIds, sizeIds, minPrice, maxPrice);
    }
}