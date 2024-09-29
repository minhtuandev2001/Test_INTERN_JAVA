package com.minhtuan.internjava.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailResponse {

    Long id;

    String name;

    List<String> images;

    String description;

    Integer view;

    Integer stock;

    Integer discount;

    BigDecimal price;

    Float rating;

    String thumbnail;

    String slug;

    CategoryResponse category;

    StyleResponse style;

    Set<ColorResponse> colors = new HashSet<>();

    Set<SizeResponse> sizes = new HashSet<>();
}
