package com.panda.quanly.taisan.quanlytaisan.batdongsan.controller;

import java.util.ArrayList;
import java.util.List;

import com.panda.quanly.taisan.quanlytaisan.batdongsan.controller.entity.BatDongSan;
import com.panda.quanly.taisan.quanlytaisan.batdongsan.controller.entity.BatDongSanRepository;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatDongSanManagement {

    @Autowired BatDongSanRepository batDongSanRepo;

    public String buildUrlHinhAnh(BatDongSan batDongSan) {
        if(batDongSan.getHinhDaiDien() == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("batdongsan/").append(batDongSan.getId()).append("/").append(batDongSan.getHinhDaiDien());
        return sb.toString();
    }

    public List<String> buildUrlDanhSachHinhAnh(BatDongSan dongSan) {
        if(dongSan.getDanhSachHinhAnh() == null || dongSan.getDanhSachHinhAnh().length() == 0) {
            return new ArrayList<String>();
        }
        JSONArray jsonArray = new JSONArray(dongSan.getDanhSachHinhAnh()); 
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < jsonArray.length(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("batdongsan/").append(dongSan.getId()).append("/").append(jsonArray.get(i).toString());
            list.add(sb.toString());
        }
        return list;
    }

    public BatDongSan updateBds(BatDongSan bds) {
        return batDongSanRepo.save(bds);
    }

    synchronized public void updateHinhDaiDien(String id, String originalFilename) {
        BatDongSan bds = batDongSanRepo.getById(Long.parseLong(id));
        bds.setHinhDaiDien(originalFilename);
        batDongSanRepo.save(bds);
    }

    synchronized public void updateHinhAnhKhac(String id, String originalFilename) {
        BatDongSan bds = batDongSanRepo.getById(Long.parseLong(id));
        String danhSachHinhAnh = bds.getDanhSachHinhAnh();
        System.out.println("File " + originalFilename);
        System.out.println("Truớc " + bds.getDanhSachHinhAnh());
        JSONArray jsonArray = new JSONArray(danhSachHinhAnh);
        jsonArray.put(originalFilename);
        System.out.println("Sau " + jsonArray.toString());
        bds.setDanhSachHinhAnh(jsonArray.toString());
        batDongSanRepo.saveAndFlush(bds);
    }

    public void cleanImages(String id) {
        BatDongSan bds = batDongSanRepo.getById(Long.parseLong(id));
        bds.setHinhDaiDien("");
        bds.setDanhSachHinhAnh("[]");
        batDongSanRepo.save(bds);
    }
}
