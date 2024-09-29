package com.minhtuan.internjava.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ColorRequest {
    @NotBlank(message = "Name color cannot be left blank")
    String name;
    @NotBlank(message = "Code color cannot be left blank")
    String code;
}
