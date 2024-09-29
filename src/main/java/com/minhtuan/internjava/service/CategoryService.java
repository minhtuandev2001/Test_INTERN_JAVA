package com.minhtuan.internjava.service;

import com.minhtuan.internjava.dto.request.CategoryRequest;
import com.minhtuan.internjava.model.CategoryEntity;

import java.util.List;

public interface CategoryService {
    CategoryEntity createCategory(CategoryEntity category);
    List<CategoryEntity> getAllCategory();
    CategoryEntity getAllCategoryById(Long id);
    void updateCategory(Long id, CategoryRequest request);
    void deleteCategoryById(Long id);
}
