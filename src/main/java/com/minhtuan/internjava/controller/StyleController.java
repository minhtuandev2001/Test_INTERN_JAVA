package com.minhtuan.internjava.controller;

import com.minhtuan.internjava.dto.request.SizeRequest;
import com.minhtuan.internjava.dto.request.StyleRequest;
import com.minhtuan.internjava.dto.response.ResponseObject;
import com.minhtuan.internjava.dto.response.SizeResponse;
import com.minhtuan.internjava.dto.response.StyleResponse;
import com.minhtuan.internjava.exception.AppException;
import com.minhtuan.internjava.model.SizeEntity;
import com.minhtuan.internjava.model.StyleEntity;
import com.minhtuan.internjava.service.StyleService;
import com.minhtuan.internjava.utils.toSlug;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/styles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StyleController {

    StyleService styleService;

    @GetMapping("/")
    public ResponseEntity<ResponseObject<?>> getAllStyle(){
        List<StyleEntity> styles = styleService.getAllStyle();
        List<StyleResponse> styleResponses = new ArrayList<>();
        for(StyleEntity style: styles){
            StyleResponse styleResponse = StyleResponse.builder()
                    .id(style.getId())
                    .name(style.getName())
                    .slug(style.getSlug())
                    .build();
            styleResponses.add(styleResponse);
        }
        return ResponseEntity.status(200).body(
                new ResponseObject<List<StyleResponse>>(200, "Get all style", styleResponses)
        );
    }

    @PostMapping("/")
    public ResponseEntity<ResponseObject<?>> createStyle(@RequestBody StyleRequest request){
        if(request.getName() == null){
            throw new AppException(400, "You need to provide a name");
        }
        StyleEntity styleEntity = StyleEntity.builder()
                .name(request.getName())
                .slug(toSlug.nameToSlug(request.getName()))
                .build();
        StyleEntity styleNew = styleService.createStyle(styleEntity);
        StyleResponse styleResponse = StyleResponse.builder()
                .id(styleNew.getId())
                .name(styleNew.getName())
                .slug(styleNew.getSlug())
                .build();

        return ResponseEntity.status(201).body(
                new ResponseObject<StyleResponse>(201, "create a tag style", styleResponse)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject<?>> updateStyle(
            @PathVariable Long id,
            @RequestBody(required = false) StyleRequest request){
        if(request.getName() == null){
            throw new AppException(400, "You need to provide a name");
        }
        styleService.updateStyle(id,request.getName());

        return ResponseEntity.status(200).body(
                new ResponseObject<String>(200, "update tag size successfully")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject<?>> getStyleById(@PathVariable Long id){
        StyleEntity styleEntity  = styleService.getStyleById(id);
        StyleResponse styleResponse = StyleResponse.builder()
                .id(styleEntity.getId())
                .name(styleEntity.getName())
                .slug(styleEntity.getSlug())
                .build();

        return ResponseEntity.status(200).body(
                new ResponseObject<StyleResponse>(200, "get style by id success", styleResponse)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject<?>> deleteStyleById(@PathVariable Long id){
        styleService.deleteStyleById(id);

        return ResponseEntity.status(200).body(
                new ResponseObject<String>(200, "delete style successfully")
        );
    }
}
