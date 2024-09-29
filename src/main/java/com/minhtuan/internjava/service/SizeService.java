package com.minhtuan.internjava.service;

import com.minhtuan.internjava.model.ColorEntity;
import com.minhtuan.internjava.model.SizeEntity;

import java.util.List;

public interface SizeService {
    SizeEntity createSize(SizeEntity size);
    void updateSize(Long id, SizeEntity size);
    List<SizeEntity> getAllSize();
    SizeEntity getSizeById(Long id);
    void deleteSizeById(Long id);
}
