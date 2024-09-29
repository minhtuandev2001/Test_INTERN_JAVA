package com.minhtuan.internjava.dto.request;

import com.minhtuan.internjava.model.CategoryEntity;
import com.minhtuan.internjava.model.ColorEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {

    @NotBlank(message = "Name cannot be left blank")
    String name;

    List<String> images;

    @NotBlank(message = "Name cannot be left blank")
    String description;

    Integer view;

    @NotBlank(message = "Stock cannot be left blank")
    Integer stock;

    Integer discount;

    @NotBlank(message = "Price cannot be left blank")
    @Min(0)
    BigDecimal price;

    Float rating;

    @NotBlank(message = "Thumbnail cannot be left blank")
    String thumbnail;

    @NotNull(message = "Category can not be null")
    Long category_id;

    @NotEmpty(message = "colorIds can not empty")
    Set<Long> colorIds;

    @NotEmpty(message = "sizeIds can not empty")
    Set<Long> sizeIds;

    @NotNull(message = "style can not be null")
    Long style_id;
}
