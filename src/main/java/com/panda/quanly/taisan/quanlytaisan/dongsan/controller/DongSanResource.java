package com.panda.quanly.taisan.quanlytaisan.dongsan.controller;

import java.util.List;
import java.util.Optional;

import com.panda.quanly.taisan.quanlytaisan.dongsan.entity.DongSan;
import com.panda.quanly.taisan.quanlytaisan.dongsan.entity.DongSanRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/dong-san")
public class DongSanResource {
    Logger logger = LoggerFactory.getLogger(DongSanResource.class);

    @Autowired DongSanRepository dongSanRepo;;
    @Autowired DongSanManagement dongSanManagement;

    @GetMapping("/all")
    public List<DongSan> all() {
        List<DongSan> findAll = dongSanRepo.findAll();
        findAll.stream().forEach(dongSan -> {
            dongSan.setDiaChiHinhAnhDaiDien(dongSanManagement.buildUrlHinhAnh(dongSan));
            dongSan.setDanhSachDiaChiHinhAnh(dongSanManagement.buildUrlDanhSachHinhAnh(dongSan));
        });
        return findAll;
    }

    @PostMapping()
    DongSan newDongSan(@RequestBody DongSan newDongSan) {
        return dongSanRepo.save(newDongSan);
    }

    @GetMapping("/{id}")
    DongSan one(@PathVariable Long id) throws Exception {
        Optional<DongSan> dongSanOpt = dongSanRepo.findById(id);
        if(dongSanOpt.isPresent()) {
          dongSanOpt.get().setDiaChiHinhAnhDaiDien(dongSanManagement.buildUrlHinhAnh(dongSanOpt.get()));
          dongSanOpt.get().setDanhSachDiaChiHinhAnh(dongSanManagement.buildUrlDanhSachHinhAnh(dongSanOpt.get()));
          return dongSanOpt.get();
        }
        throw new Exception();
    }

    @PutMapping("/{id}")
    DongSan replaceEmployee(@RequestBody DongSan newDongSan, @PathVariable Long id) {
        return newDongSan;
        // return dongSanRepo.findById(id)
        // .map(dongSan -> {
        //     dongSan.setName(newDongSan.getName());
        //     dongSan.setRole(newDongSan.getRole());
        //     return dongSanRepo.save(dongSan);
        // })
        // .orElseGet(() -> {
        //     newDongSan.setId(id);
        //     return repository.save(newDongSan);
        // });
    }

    @DeleteMapping("/{id}")
    void deleteDongSan(@PathVariable Long id) {
        dongSanRepo.deleteById(id);
    }
}
