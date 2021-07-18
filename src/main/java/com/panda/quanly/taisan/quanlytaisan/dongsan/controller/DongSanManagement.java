package com.panda.quanly.taisan.quanlytaisan.dongsan.controller;

import java.util.ArrayList;
import java.util.List;

import com.panda.quanly.taisan.quanlytaisan.dongsan.entity.DongSan;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public class DongSanManagement {
    
    public String buildUrlHinhAnh(DongSan dongSan) {
        if(dongSan.getHinhAnhHienThi() == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("dongsan/").append(dongSan.getId()).append("/").append(dongSan.getHinhAnhHienThi());
        return sb.toString();
    }

    public List<String> buildUrlDanhSachHinhAnh(DongSan dongSan) {
        if(dongSan.getDanhSachHinhAnh() == null || dongSan.getDanhSachHinhAnh().length() == 0) {
            return new ArrayList<String>();
        }
        JSONArray jsonArray = new JSONArray(dongSan.getDanhSachHinhAnh()); 
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < jsonArray.length(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("dongsan/").append(dongSan.getId()).append("/").append(jsonArray.get(i).toString());
            list.add(sb.toString());
        }
        return list;
    }
}
