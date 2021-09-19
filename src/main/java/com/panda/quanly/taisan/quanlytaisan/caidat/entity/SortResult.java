package com.panda.quanly.taisan.quanlytaisan.caidat.entity;
import java.util.List;
import lombok.Data;

@Data
public class SortResult {
    List<SortItem> homepage;
    List<SortItem> full;
    List<SortItem> hidden;
}
