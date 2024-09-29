package com.minhtuan.internjava.service.Impl;

import com.minhtuan.internjava.exception.AppException;
import com.minhtuan.internjava.model.ColorEntity;
import com.minhtuan.internjava.repository.ColorRepository;
import com.minhtuan.internjava.service.ColorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ColorServiceImp implements ColorService {
    ColorRepository colorRepository;


    @Override
    public ColorEntity createColor(ColorEntity color) {

        boolean existColor = colorRepository.existsByCode(color.getCode());
        if(existColor){
            throw new AppException(400, "color already exists");
        }
        return colorRepository.save(color);
    }

    @Override
    public void updateColor(Long id, ColorEntity color) {
        ColorEntity colorFromDB = colorRepository.findById(id)
                .orElseThrow(()-> new AppException(404, "color not found"));

        ColorEntity colorNew = ColorEntity.builder()
                .id(id)
                .code(color.getCode() == null ? colorFromDB.getCode(): color.getCode())
                .name(color.getName() == null ? colorFromDB.getName() : color.getName())
                .products(colorFromDB.getProducts())
                .build();
        colorRepository.save(colorNew);
    }

    @Override
    public List<ColorEntity> getAllColor() {
        return colorRepository.findAll();
    }

    @Override
    public ColorEntity getColorById(Long id) {
        return colorRepository.findById(id).orElseThrow(() -> new AppException(404, "color not found"));
    }

    @Override
    public void deleteColorById(Long id) {
        boolean existColor = colorRepository.existsById(id);

        if(!existColor){
            throw new AppException(404, "color not found");
        }
        colorRepository.deleteById(id);
    }
}
