package com.minhtuan.internjava.service.Impl;

import com.minhtuan.internjava.dto.request.CategoryRequest;
import com.minhtuan.internjava.exception.AppException;
import com.minhtuan.internjava.model.CategoryEntity;
import com.minhtuan.internjava.repository.CategoryRepository;
import com.minhtuan.internjava.service.CategoryService;
import com.minhtuan.internjava.utils.toSlug;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImp implements CategoryService {

    CategoryRepository categoryRepository;

    @Override
    public CategoryEntity createCategory(CategoryEntity category) {
        boolean existCategory = categoryRepository.existsByName(category.getName());
        if(existCategory){
            throw new AppException(400, "This category already exists");
        }
        return categoryRepository.save(category);
    }

    @Override
    public List<CategoryEntity> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryEntity getAllCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()-> new AppException(404, "category not found"));
    }

    @Override
    public void updateCategory(Long id, CategoryRequest request) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(()-> new AppException(404, "category not found"));
        CategoryEntity category = CategoryEntity.builder()
                .id(id)
                .name(request.getName() == null ? categoryEntity.getName(): request.getName())
                .description(request.getDescription() == null ? categoryEntity.getDescription() : request.getDescription())
                .thumbnail(request.getThumbnail()== null ? categoryEntity.getThumbnail() : request.getThumbnail())
                .slug(request.getName() == null ? toSlug.nameToSlug(categoryEntity.getName()): toSlug.nameToSlug(request.getName()))
                .products(categoryEntity.getProducts())
                .build();
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategoryById(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(()-> new AppException(404, "category not found"));
        categoryRepository.deleteById(id);
    }
}
