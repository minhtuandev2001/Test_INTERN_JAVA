package com.minhtuan.internjava.controller;

import com.minhtuan.internjava.dto.request.ColorRequest;
import com.minhtuan.internjava.dto.response.ColorResponse;
import com.minhtuan.internjava.dto.response.ResponseObject;
import com.minhtuan.internjava.model.ColorEntity;
import com.minhtuan.internjava.service.ColorService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/colors")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ColorController {

    ColorService colorService;

    @GetMapping("/")
    public ResponseEntity<ResponseObject<?>> getAllColor(){
        List<ColorEntity> colors = colorService.getAllColor();
        List<ColorResponse> colorResponses = new ArrayList<>();
        for(ColorEntity color: colors){
            ColorResponse colorResponse = ColorResponse.builder()
                    .id(color.getId())
                    .code(color.getCode())
                    .name(color.getName())
                    .build();
            colorResponses.add(colorResponse);
        }

        return ResponseEntity.status(200).body(
                new ResponseObject<List<ColorResponse>>(200, "Get all color", colorResponses)
        );
    }

    @PostMapping("/")
    public ResponseEntity<ResponseObject<?>> createColor(@Valid @RequestBody ColorRequest request){
        ColorEntity colorEntity = ColorEntity.builder()
                .name(request.getName())
                .code(request.getCode())
                .build();
        ColorEntity colorNew = colorService.createColor(colorEntity);
        ColorResponse colorResponse = ColorResponse.builder()
                .id(colorNew.getId())
                .name(colorNew.getName())
                .code(colorNew.getCode())
                .build();

        return ResponseEntity.status(201).body(
                new ResponseObject<ColorResponse>(201, "create a tag color", colorResponse)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject<?>> updateColor(
            @PathVariable Long id,
            @RequestBody(required = false) ColorRequest request){
        ColorEntity colorEntity = ColorEntity.builder()
                .id(id)
                .name(request.getName())
                .code(request.getCode())
                .build();
        colorService.updateColor(id,colorEntity);

        return ResponseEntity.status(200).body(
                new ResponseObject<String>(200, "update tag color successfully")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject<?>> getColorById(@PathVariable Long id){
        ColorEntity colorEntity  = colorService.getColorById(id);
        ColorResponse colorResponse = ColorResponse.builder()
                .id(colorEntity.getId())
                .code(colorEntity.getCode())
                .name(colorEntity.getName())
                .build();

        return ResponseEntity.status(200).body(
                new ResponseObject<ColorResponse>(200, "get color by id success", colorResponse)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject<?>> deleteColorById(@PathVariable Long id){
        colorService.deleteColorById(id);

        return ResponseEntity.status(200).body(
                new ResponseObject<String>(200, "delete color successfully ")
        );
    }
}
