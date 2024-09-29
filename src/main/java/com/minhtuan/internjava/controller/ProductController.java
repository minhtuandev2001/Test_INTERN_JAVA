package com.minhtuan.internjava.controller;

import com.minhtuan.internjava.dto.request.ProductRequest;
import com.minhtuan.internjava.dto.response.*;
import com.minhtuan.internjava.model.ColorEntity;
import com.minhtuan.internjava.model.ProductEntity;
import com.minhtuan.internjava.model.SizeEntity;
import com.minhtuan.internjava.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ProductController {

    ProductService productService;

//    @GetMapping("/")
//    public ResponseEntity<ResponseObject<?>> getAllProduct(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int pageSize
//            ){
//        return ResponseEntity.ok(
//                new ResponseObject<PageResponse<?>>(
//                        200, "get all product",
//                        productService.getAllProduct(page, pageSize)
//                )
//        );
//    }
    @GetMapping("/")
    public ResponseEntity<ResponseObject<?>> getAllProduct(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) Long category_id,
            @RequestParam(required = false) Long style_id,
            @RequestParam(required = false) List<Long> colorIds,
            @RequestParam(required = false) List<Long> sizeIds,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
            ){

        return ResponseEntity.ok(
                new ResponseObject<PageResponse<?>>(
                        200, "get all product",
                        productService.getAllProductByFilter(page, pageSize, category_id, style_id, colorIds, sizeIds, minPrice, maxPrice)
                )
        );
    }

    @PostMapping("/")
    public ResponseEntity<ResponseObject<?>> createProduct(@RequestBody ProductRequest request){
        ProductEntity product = productService.createProduct(request);
        ProductDetailResponse productNew = getProductResponse(product);
        return ResponseEntity.ok(
                new ResponseObject<ProductDetailResponse>(
                        201, "create product successfully",
                        productNew
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject<?>> deleteProductById(@PathVariable Long id){
        productService.deleteProductById(id);
        return ResponseEntity.ok(
                new ResponseObject<String>(
                        200, "delete product successfully"
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject<?>> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest request){
        productService.updateProduct(id, request);
        return ResponseEntity.ok(
                new ResponseObject<String>(
                        200, "update product successfully"
                )
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject<?>> getProductById(@PathVariable Long id){
        ProductEntity productEntity = productService.getProductById(id);
        ProductDetailResponse productDetail = getProductResponse(productEntity);
        return ResponseEntity.ok(
                new ResponseObject<ProductDetailResponse>(
                        200, "get product successfully",
                        productDetail
                )
        );
    }

    private ProductDetailResponse getProductResponse(ProductEntity product){
        ProductDetailResponse productNew  = ProductDetailResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .images(Arrays.asList(product.getImages().split(",")))
                .stock(product.getStock())
                .description(product.getDescription())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .thumbnail(product.getThumbnail())
                .build();
        CategoryResponse category = CategoryResponse.builder()
                .id(product.getCategory().getId())
                .name(product.getCategory().getName())
                .build();
        productNew.setCategory(category);
        StyleResponse style = StyleResponse.builder()
                .id(product.getStyle().getId())
                .name(product.getStyle().getName())
                .slug(product.getStyle().getSlug())
                .build();
        productNew.setStyle(style);
        Set<ColorResponse> colors = new HashSet<>();
        for(ColorEntity color : product.getColors()){
            ColorResponse colorResponse = ColorResponse.builder()
                    .id(color.getId())
                    .name(color.getName())
                    .code(color.getCode())
                    .build();
            colors.add(colorResponse);
        }
        productNew.setColors(colors);
        Set<SizeResponse> sizes = new HashSet<>();
        for(SizeEntity size : product.getSizes()){
            SizeResponse sizeResponse = SizeResponse.builder()
                    .id(size.getId())
                    .name(size.getName())
                    .code(size.getCode())
                    .build();
            sizes.add(sizeResponse);
        }
        productNew.setSizes(sizes);

        return productNew;
    }
}
