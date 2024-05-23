package com.net.usermanager.service.impl;

import com.net.usermanager.entity.UnitEntity;
import com.net.usermanager.repository.UnitRespository;
import com.net.usermanager.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    private UnitRespository unitRespository;

    @Override
    public UnitEntity findByUnitName(String unitName) {
        return unitRespository.findByUnitName(unitName);
    }

    @Override
    public Optional<UnitEntity> findByUnitId(Long id) {
        return unitRespository.findById(id);
    }

    @Override
    public UnitEntity saveOrUpdate(UnitEntity unit) {
        return unitRespository.save(unit);
    }

    @Override
    public List<UnitEntity> findAll() {
        return unitRespository.findAll();
    }

    @Override
    public void delete(UnitEntity unitEntity) {
        unitRespository.delete(unitEntity);
    }

    @Override
    public boolean existsByUnitName(String unitName) {
        return unitRespository.existsByUnitName(unitName);
    }

    @Override
    public Optional<UnitEntity> findById(Long id) {
        return unitRespository.findById(id);
    }
}
