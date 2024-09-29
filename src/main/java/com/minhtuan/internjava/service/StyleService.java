package com.minhtuan.internjava.service;

import com.minhtuan.internjava.model.StyleEntity;

import java.util.List;

public interface StyleService {
    StyleEntity createStyle(StyleEntity style);
    List<StyleEntity> getAllStyle();
    void updateStyle(Long id, String name);
    StyleEntity getStyleById(Long id);
    void deleteStyleById(Long id);
}
