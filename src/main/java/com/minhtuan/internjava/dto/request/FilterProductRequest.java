package com.minhtuan.internjava.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilterProductRequest {
    Long category_id;
    Long style_id;
    List<Long> colorIds;
    List<Long> sizeIds;
    BigDecimal minPrice;
    BigDecimal maxPrice;
}
