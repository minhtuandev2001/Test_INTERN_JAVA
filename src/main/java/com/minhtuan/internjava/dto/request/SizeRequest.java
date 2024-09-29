package com.minhtuan.internjava.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SizeRequest {

    @NotBlank(message = "Name size cannot be left blank")
    String name;
    @NotBlank(message = "Code size cannot be left blank")
    String code;
}
