package com.minhtuan.internjava.repository;

import com.minhtuan.internjava.model.StyleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StyleRepository extends JpaRepository<StyleEntity, Long> {
    boolean existsByName(String name);
}
