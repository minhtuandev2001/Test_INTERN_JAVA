package com.minhtuan.internjava.controller;

import com.minhtuan.internjava.dto.request.CategoryRequest;
import com.minhtuan.internjava.dto.response.CategoryResponse;
import com.minhtuan.internjava.dto.response.ResponseObject;
import com.minhtuan.internjava.mapper.CategoryMapper;
import com.minhtuan.internjava.model.CategoryEntity;
import com.minhtuan.internjava.service.CategoryService;
import com.minhtuan.internjava.utils.toSlug;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/category")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {

    CategoryService categoryService;
    CategoryMapper categoryMapper;

    @GetMapping("/")
    public ResponseEntity<ResponseObject<?>> getAllCategory(){
        List<CategoryEntity> categoryEntitys = categoryService.getAllCategory();
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        for(CategoryEntity category: categoryEntitys){
            CategoryResponse categoryResponse = categoryMapper.toCategoryResponse(category);
            categoryResponses.add(categoryResponse);
        }        return ResponseEntity.status(200).body(
                new ResponseObject<List<CategoryResponse>>(
                        200, "get all category successfully",
                        categoryResponses
                )
        );
    }

    @PostMapping("/")
    public ResponseEntity<ResponseObject<?>> createCategory(@RequestBody CategoryRequest request){
        CategoryEntity categoryEntity = categoryMapper.toCategoryEntity(request);
        // tạo slug
        categoryEntity.setSlug(toSlug.nameToSlug(categoryEntity.getName()));
        // chuyển đến service
        CategoryEntity categoryNew = categoryService.createCategory(categoryEntity);

        return ResponseEntity.status(201).body(
                new ResponseObject<CategoryResponse>(
                        201, "create category successfully",
                        categoryMapper.toCategoryResponse(categoryNew)
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject<?>> getAllCategoryById(@PathVariable Long id){
        CategoryEntity categoryEntity = categoryService.getAllCategoryById(id);
        CategoryResponse categoryResponse = categoryMapper.toCategoryResponse(categoryEntity);
        return ResponseEntity.status(200).body(
                new ResponseObject<CategoryResponse>(
                        200, "get category by id successfully",
                        categoryResponse
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject<?>> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest request){
        categoryService.updateCategory(id,request);
        return ResponseEntity.status(200).body(
                new ResponseObject<String>(
                        200, "update category successfully"
                )
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject<?>> deleteCategoryById(@PathVariable Long id){
        categoryService.deleteCategoryById(id);
        return ResponseEntity.status(200).body(
                new ResponseObject<String>(
                        200, "delete category successfully"
                )
        );
    }
}
