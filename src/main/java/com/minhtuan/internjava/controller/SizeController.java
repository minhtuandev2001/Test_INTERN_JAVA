package com.minhtuan.internjava.controller;

import com.minhtuan.internjava.dto.request.SizeRequest;
import com.minhtuan.internjava.dto.response.ResponseObject;
import com.minhtuan.internjava.dto.response.SizeResponse;
import com.minhtuan.internjava.model.SizeEntity;
import com.minhtuan.internjava.service.SizeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/sizes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SizeController {

    SizeService sizeService;

    @GetMapping("/")
    public ResponseEntity<ResponseObject<?>> getAllSize(){
        List<SizeEntity> sizes = sizeService.getAllSize();
        List<SizeResponse> sizeResponses = new ArrayList<>();
        for(SizeEntity size: sizes){
            SizeResponse sizeResponse = SizeResponse.builder()
                    .id(size.getId())
                    .code(size.getCode())
                    .name(size.getName())
                    .build();
            sizeResponses.add(sizeResponse);
        }

        return ResponseEntity.status(200).body(
                new ResponseObject<List<SizeResponse>>(200, "Get all size", sizeResponses)
        );
    }

    @PostMapping("/")
    public ResponseEntity<ResponseObject<?>> createSize(@Valid @RequestBody SizeRequest request){
        SizeEntity sizeEntity = SizeEntity.builder()
                .name(request.getName())
                .code(request.getCode())
                .build();
        SizeEntity sizeNew = sizeService.createSize(sizeEntity);
        SizeResponse sizeResponse = SizeResponse.builder()
                .id(sizeNew.getId())
                .name(sizeNew.getName())
                .code(sizeNew.getCode())
                .build();

        return ResponseEntity.status(201).body(
                new ResponseObject<SizeResponse>(201, "create a tag size", sizeResponse)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject<?>> updateSize(
            @PathVariable Long id,
            @RequestBody(required = false) SizeRequest request){
        SizeEntity sizeEntity = SizeEntity.builder()
                .id(id)
                .name(request.getName())
                .code(request.getCode())
                .build();
        sizeService.updateSize(id,sizeEntity);

        return ResponseEntity.status(200).body(
                new ResponseObject<String>(200, "update tag size successfully")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject<?>> getSizeById(@PathVariable Long id){
        SizeEntity sizeEntity  = sizeService.getSizeById(id);
        SizeResponse sizeResponse = SizeResponse.builder()
                .id(sizeEntity.getId())
                .code(sizeEntity.getCode())
                .name(sizeEntity.getName())
                .build();

        return ResponseEntity.status(200).body(
                new ResponseObject<SizeResponse>(200, "get size by id success", sizeResponse)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject<?>> deleteSizeById(@PathVariable Long id){
        sizeService.deleteSizeById(id);

        return ResponseEntity.status(200).body(
                new ResponseObject<String>(200, "delete size successfully ")
        );
    }
}
