package com.minhtuan.internjava.service;

import com.minhtuan.internjava.model.ColorEntity;

import java.util.List;

public interface ColorService {
    ColorEntity createColor(ColorEntity color);
    void updateColor(Long id, ColorEntity color);
    List<ColorEntity> getAllColor();
    ColorEntity getColorById(Long id);
    void deleteColorById(Long id);
}
