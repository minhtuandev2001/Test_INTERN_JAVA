package com.minhtuan.internjava.service.Impl;

import com.minhtuan.internjava.exception.AppException;
import com.minhtuan.internjava.model.StyleEntity;
import com.minhtuan.internjava.repository.StyleRepository;
import com.minhtuan.internjava.service.StyleService;
import com.minhtuan.internjava.utils.toSlug;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StyleServiceImp implements StyleService {

    StyleRepository styleRepository;

    @Override
    public StyleEntity createStyle(StyleEntity style) {
        boolean existStyle = styleRepository.existsByName(style.getName());
        if(existStyle){
            throw new AppException(400, "This style already exists");
        }
        return styleRepository.save(style);
    }

    @Override
    public List<StyleEntity> getAllStyle() {
        return styleRepository.findAll();
    }

    @Override
    public void updateStyle(Long id, String name) {
        boolean existStyleName = styleRepository.existsByName(name);
        if(existStyleName){
            throw new AppException(400, "This style already exists");
        }
        StyleEntity existStyle = styleRepository.findById(id)
                .orElseThrow(()-> new AppException(404, "style not found"));

        StyleEntity style = StyleEntity.builder()
                .id(id)
                .name(name)
                .slug(toSlug.nameToSlug(name))
                .products(existStyle.getProducts())
                .build();
        styleRepository.save(style);
    }

    @Override
    public StyleEntity getStyleById(Long id) {
        return styleRepository.findById(id).orElseThrow(()-> new AppException(404, "style not found"));
    }

    @Override
    public void deleteStyleById(Long id) {
        StyleEntity style = styleRepository.findById(id).orElseThrow(()-> new AppException(404, "style not found"));
        styleRepository.deleteById(id);
    }
}
