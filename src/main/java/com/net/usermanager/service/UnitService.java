package com.net.usermanager.service;

import com.net.usermanager.entity.UnitEntity;

import java.util.List;
import java.util.Optional;

public interface UnitService {
    UnitEntity findByUnitName(String unitName);

    Optional<UnitEntity> findByUnitId(Long id);
    UnitEntity saveOrUpdate(UnitEntity unit);

    List<UnitEntity> findAll();
    void delete(UnitEntity unitEntity);

    boolean existsByUnitName(String unitName);

    Optional<UnitEntity> findById(Long id);
}
