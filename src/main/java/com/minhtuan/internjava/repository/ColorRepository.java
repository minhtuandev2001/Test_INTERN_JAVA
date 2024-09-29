package com.minhtuan.internjava.repository;

import com.minhtuan.internjava.model.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<ColorEntity, Long> {

    boolean existsByCode(String code);
}
