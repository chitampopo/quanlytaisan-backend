package com.panda.quanly.taisan.quanlytaisan.batdongsan.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.panda.quanly.taisan.quanlytaisan.batdongsan.controller.entity.BatDongSan;
import com.panda.quanly.taisan.quanlytaisan.batdongsan.controller.entity.BatDongSanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/bat-dong-san")
public class BatDongSanResource {
    
    @Autowired BatDongSanRepository batDongSanRepo;
    @Autowired BatDongSanManagement batDongSanManagement;

    @GetMapping("/all")
    public List<BatDongSan> all() {
        List<BatDongSan> findAll = batDongSanRepo.findAll().stream().filter(batDongSan -> batDongSan.getIsActive() != Boolean.FALSE).collect(Collectors.toList());
        findAll.stream().forEach(batDongSan -> {
            batDongSan.setUrlHinhDaiDien(batDongSanManagement.buildUrlHinhAnh(batDongSan));
            batDongSan.setDanhSachUrlHinhAnh(batDongSanManagement.buildUrlDanhSachHinhAnh(batDongSan));
        });
        return findAll;
    }

    @GetMapping("/{id}")
    BatDongSan one(@PathVariable Long id) throws Exception {
        Optional<BatDongSan> dongSanOpt = batDongSanRepo.findById(id);
        if(dongSanOpt.isPresent()) {
          dongSanOpt.get().setUrlHinhDaiDien(batDongSanManagement.buildUrlHinhAnh(dongSanOpt.get()));
          dongSanOpt.get().setDanhSachUrlHinhAnh(batDongSanManagement.buildUrlDanhSachHinhAnh(dongSanOpt.get()));
          return dongSanOpt.get();
        }
        throw new Exception();
    }

    @PostMapping
    BatDongSan updateBds(@RequestBody BatDongSan bds) {
        return batDongSanManagement.updateBds(bds);
    }

}
