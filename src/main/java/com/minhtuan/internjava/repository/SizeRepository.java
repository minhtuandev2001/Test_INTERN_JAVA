package com.minhtuan.internjava.repository;

import com.minhtuan.internjava.model.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeRepository extends JpaRepository<SizeEntity, Long> {
    boolean existsByCode(String code);
}
