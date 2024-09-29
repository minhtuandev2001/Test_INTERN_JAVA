package com.minhtuan.internjava.service.Impl;

import com.minhtuan.internjava.exception.AppException;
import com.minhtuan.internjava.model.ColorEntity;
import com.minhtuan.internjava.model.SizeEntity;
import com.minhtuan.internjava.repository.SizeRepository;
import com.minhtuan.internjava.service.SizeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SizeServiceImp implements SizeService {

    SizeRepository sizeRepository;

    @Override
    public List<SizeEntity> getAllSize() {
        return sizeRepository.findAll();
    }

    @Override
    public SizeEntity createSize(SizeEntity size) {
        boolean existColor = sizeRepository.existsByCode(size.getCode());
        if(existColor){
            throw new AppException(400, "size already exists");
        }
        return sizeRepository.save(size);
    }

    @Override
    public void updateSize(Long id, SizeEntity size) {
        SizeEntity sizeFromDB = sizeRepository.findById(id)
                .orElseThrow(()-> new AppException(404, "size not found"));

        SizeEntity sizeNew = SizeEntity.builder()
                .id(id)
                .code(size.getCode() == null ? sizeFromDB.getCode(): size.getCode())
                .name(size.getName() == null ? sizeFromDB.getName() : size.getName())
                .products(sizeFromDB.getProducts())
                .build();
        sizeRepository.save(sizeNew);
    }

    @Override
    public SizeEntity getSizeById(Long id) {
        return sizeRepository.findById(id).orElseThrow(()-> new AppException(404, "size not found"));
    }

    @Override
    public void deleteSizeById(Long id) {
        boolean existSize = sizeRepository.existsById(id);

        if(!existSize){
            throw new AppException(404, "size not found");
        }
        sizeRepository.deleteById(id);
    }
}
