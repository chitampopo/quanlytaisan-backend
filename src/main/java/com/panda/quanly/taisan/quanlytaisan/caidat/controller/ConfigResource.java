package com.panda.quanly.taisan.quanlytaisan.caidat.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.panda.quanly.taisan.quanlytaisan.caidat.entity.Config;
import com.panda.quanly.taisan.quanlytaisan.caidat.entity.ConfigRepository;
import com.panda.quanly.taisan.quanlytaisan.caidat.entity.SortItem;
import com.panda.quanly.taisan.quanlytaisan.caidat.entity.SortResult;
import com.panda.quanly.taisan.quanlytaisan.caidat.entity.UpdateSortData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/configs")
public class ConfigResource {
    
    @Autowired ConfigRepository configRepo;

    @GetMapping("/sap-xep-bds")
    public SortResult sortBDS() {
        List<Config> findAll = configRepo.findAll();
        Optional<Config> homePageBds = findAll.stream().filter(item -> item.getKey().equals("HOMEPAGE_BDS")).findAny();
        Optional<Config> fullBds = findAll.stream().filter(item -> item.getKey().equals("FULL_BDS")).findAny();
        Optional<Config> hiddenBds = findAll.stream().filter(item -> item.getKey().equals("HIDDEN_BDS")).findAny();

        SortResult result = new SortResult();
        result.setHomepage(configToListString(homePageBds));
        result.setFull(configToListString(fullBds));
        result.setHidden(configToListString(hiddenBds));
        return result;
    }

    @GetMapping("/sap-xep-ds")
    public SortResult sortDS() {
        List<Config> findAll = configRepo.findAll();
        Optional<Config> homepage = findAll.stream().filter(item -> item.getKey().equals("HOMEPAGE_DS")).findAny();
        Optional<Config> full = findAll.stream().filter(item -> item.getKey().equals("FULL_DS")).findAny();
        Optional<Config> hidden = findAll.stream().filter(item -> item.getKey().equals("HIDDEN_DS")).findAny();

        SortResult result = new SortResult();
        result.setHomepage(configToListString(homepage));
        result.setFull(configToListString(full));
        result.setHidden(configToListString(hidden));
        return result;
    }

    @PutMapping("/update-sort/{type}/{key}")
    public void updateSort(@PathVariable String type, @PathVariable String key, @RequestBody UpdateSortData data) {
        configRepo.findAll()
            .stream()
            .filter(c -> {
                String combinedKey = key + "_" + type;
                return c.getKey().equalsIgnoreCase(combinedKey);
            })
            .findFirst()
            .ifPresent(c -> {
                String newValue = data.getData().stream().map(item -> item.getId()).collect(Collectors.joining(","));
                c.setValue(newValue);
                configRepo.save(c);
            });
        
        data.getData().stream().forEach(item -> {
            System.out.println(item);
        });
    }

    private List<SortItem> configToListString(Optional<Config> config) {
        if(!config.isPresent()) {
            return Collections.emptyList();
        }
        return List.of(config.get().getValue().split(",")).stream().map(item -> {
            SortItem sortItem = new SortItem();
            sortItem.setId(item);
            return sortItem;
        }).collect(Collectors.toList());
    }
}
