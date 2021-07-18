package com.panda.quanly.taisan.quanlytaisan.dongsan.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name = "dong_san")
public class DongSan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String ten;

    @Column(nullable = true)
    private String hangXe;

    @Column(nullable = true)
    private String dongXe;

    @Column(nullable = true)
    private String ghiChu;

    @Column(nullable = true)
    private String diaChi;

    @Column(nullable = true)
    private String namSanXuat;

    @Column(nullable = true)
    private String soChoNgoi;

    @Column(nullable = true)
    private Long gia;

    @Column(nullable = true)
    private String giaBangChu;

    @Column(nullable = true)
    private String ghiChuGia;

    @Column(nullable = true)
    private String hinhAnhHienThi;
    
    @Column(nullable = true)
    private String danhSachHinhAnh;

    @Transient
    private String diaChiHinhAnhDaiDien;
    @Transient
    private List<String> danhSachDiaChiHinhAnh;
}
