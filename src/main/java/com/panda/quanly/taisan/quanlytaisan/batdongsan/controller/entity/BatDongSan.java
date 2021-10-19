package com.panda.quanly.taisan.quanlytaisan.batdongsan.controller.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import lombok.Data;

@Entity
@Table(name = "bat_dong_san")
@Data
public class BatDongSan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String ten;

    @Column(nullable = true)
    Long gia;

    @Column(nullable = true)
    String giaBangChu;

    @Column(nullable = true)
    String huong;

    @Column(nullable = true)
    String mucDichSuDung;

    @Column(nullable = true)
    String diaChi;

    @Column(nullable = true)
    String dienTichDat;

    @Column(nullable = true)
    String dienTichNha;

    @Column(nullable = true)
    String viTriGoogleMap;

    @Column(nullable = true)
    String hinhDaiDien;

    @Column(nullable = true)
    String danhSachHinhAnh = "[]";

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    String ghiChu;

    Boolean isActive = true;

    @Transient
    String urlHinhDaiDien;
    @Transient
    List<String> danhSachUrlHinhAnh;
}
