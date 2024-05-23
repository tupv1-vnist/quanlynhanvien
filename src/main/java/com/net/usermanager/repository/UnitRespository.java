package com.net.usermanager.repository;

import com.net.usermanager.entity.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRespository extends JpaRepository<UnitEntity,Long> {
    UnitEntity findByUnitName(String unitName);
    boolean existsByUnitName(String unitName);
}
