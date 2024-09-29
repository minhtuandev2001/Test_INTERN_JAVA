package com.minhtuan.internjava.mapper;

import com.minhtuan.internjava.dto.request.CategoryRequest;
import com.minhtuan.internjava.dto.response.CategoryResponse;
import com.minhtuan.internjava.model.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryEntity toCategoryEntity(CategoryRequest request);
    CategoryResponse toCategoryResponse(CategoryEntity response);
}
