package com.net.usermanager.controller;

import com.net.usermanager.entity.UnitEntity;
import com.net.usermanager.payload.response.MessageResponse;
import com.net.usermanager.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/unit")
public class UnitController {
    @Autowired
    private UnitService unitService;

    @GetMapping("/all-unit")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> showAllUnit(){
        return ResponseEntity.ok(unitService.findAll());
    }

    @DeleteMapping("/delete-unit")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUnit(UnitEntity unitEntity){
        try {
            unitService.delete(unitEntity);
            return ResponseEntity.ok("Da xoa thanh cong");
        }catch (Exception e){
            return ResponseEntity.ok("Khong tim thay");
        }
    }

    @PostMapping("/add-unit")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addUnit(@RequestBody UnitEntity unitEntity){
        try{
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String adminName= authentication.getName();
            if (unitService.existsByUnitName(unitEntity.getUnitName())){
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Unitname is already"));
            }else {
                unitEntity.setCreateBy(adminName);
                unitEntity.setCreatedDate(new Date());
                unitService.saveOrUpdate(unitEntity);
                return ResponseEntity.ok(unitEntity);
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<?> updateUnit(@PathVariable("id") Long id,@RequestBody UnitEntity updateEntity){
        try{
            Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
            String adminUpdateName= authentication.getName();
            return unitService.findById(id)
                    .map(unit -> {
                        unit.setUnitName(updateEntity.getUnitName());
                        unit.setUnitCode(updateEntity.getUnitCode());
                        unit.setUpdateBy(updateEntity.getUpdateBy());
                        unit.setDescription(updateEntity.getDescription());
                        unit.setUpdateBy(adminUpdateName);
                        unit.setUpdateDate(new Date());
                        unitService.saveOrUpdate(unit);
                        return ResponseEntity.ok(updateEntity);
                    })
                    .orElse(ResponseEntity.notFound().build());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }

    }
}
