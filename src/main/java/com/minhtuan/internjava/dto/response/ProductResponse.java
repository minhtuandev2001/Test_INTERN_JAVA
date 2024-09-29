package com.minhtuan.internjava.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    Long id;

    String name;

    Integer discount;

    BigDecimal price;

    Float rating;

    String thumbnail;

}
