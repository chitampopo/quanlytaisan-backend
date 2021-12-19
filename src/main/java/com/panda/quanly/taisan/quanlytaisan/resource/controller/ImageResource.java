package com.panda.quanly.taisan.quanlytaisan.resource.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import com.panda.quanly.taisan.quanlytaisan.batdongsan.controller.BatDongSanManagement;
import com.panda.quanly.taisan.quanlytaisan.caidat.controller.ConfigResource;
import com.panda.quanly.taisan.quanlytaisan.caidat.entity.Config;
import com.panda.quanly.taisan.quanlytaisan.dongsan.controller.DongSanManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/images")
public class ImageResource {
    
    @Autowired BatDongSanManagement batDongSanManagement;
    @Autowired DongSanManagement dongSanManagement;
    @Autowired ConfigResource configResource;

    @GetMapping(value = "/{type}/{id}/{imageFile}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable String type, @PathVariable String id, @PathVariable String imageFile) throws IOException {
        File file;
        if("banners".equals(type)) {
            Optional<Config> banner = configResource.getConfigs().stream().filter(c -> id.equals(c.getKey())).findAny();
            file = ResourceUtils.getFile("classpath:" + id + "/" + banner.get().getValue());
        } else {
            file = ResourceUtils.getFile("classpath:" + type + "/" + id + "/" + imageFile);
        }

        try (InputStream is = new FileInputStream(file)) {
            System.out.println(is);
            return is.readAllBytes();
        }
    }

    @PostMapping(value = "/upload")
    public void postImage(@RequestParam("file") MultipartFile multipartFile, @RequestParam String folder, @RequestParam String id) throws IOException {
        Files.createDirectories(Paths.get("classpath:" + folder + "/" + id));
        File file = new File("src/main/resources/" + folder + "/" + id + "/" + multipartFile.getOriginalFilename());
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(multipartFile.getBytes());
        }
        if("batdongsan".equals(folder)) {
            batDongSanManagement.updateHinhDaiDien(id, multipartFile.getOriginalFilename());
        } else {
            dongSanManagement.updateHinhDaiDien(id, multipartFile.getOriginalFilename());
        }
    }

    @PostMapping(value = "/upload-others")
    public void postImageOthers(@RequestParam("file") MultipartFile multipartFile, @RequestParam String folder, @RequestParam String id) throws IOException {
        Files.createDirectories(Paths.get("classpath:" + folder + "/" + id));
        File file = new File("src/main/resources/" + folder + "/" + id + "/" + multipartFile.getOriginalFilename());
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(multipartFile.getBytes());
        }
        if("batdongsan".equals(folder)) {
            batDongSanManagement.updateHinhAnhKhac(id, multipartFile.getOriginalFilename());
        } else {
            dongSanManagement.updateHinhAnhKhac(id, multipartFile.getOriginalFilename());
        }
    }

    @PostMapping(value = "/upload-banners")
    public void postBanners(@RequestParam("file") MultipartFile multipartFile, @RequestParam String folder) throws IOException {
        Files.createDirectories(Paths.get("classpath:" + folder));
        File file = new File("src/main/resources/" + folder + "/" + multipartFile.getOriginalFilename());
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(multipartFile.getBytes());
        }
        configResource.updateBanners(folder, multipartFile.getOriginalFilename());
    }

    @DeleteMapping(value = "/clean-folder")
    public void cleanFileInFolder(@RequestParam String folder, @RequestParam String id) throws IOException {
        Files.walk(Paths.get("src/main/resources/" + folder + "/" + id ))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .forEach(File::delete);
        
        if("batdongsan".equals(folder)) {
            batDongSanManagement.cleanImages(id);
        } else {
            dongSanManagement.cleanImages(id);
        }
    }
}
