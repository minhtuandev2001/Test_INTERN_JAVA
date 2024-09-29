package com.minhtuan.internjava.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StyleRequest {

    @NotBlank(message = "Name style cannot be left blank")
    private String name;
}
