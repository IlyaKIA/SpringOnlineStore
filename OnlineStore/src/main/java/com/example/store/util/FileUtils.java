package com.example.store.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class FileUtils {

    @Value("${main_catalog_address}")
    private static String CATALOG;

    private static final String IMAGE_FOLDER_PATH = "/data/images";
    private static final String PRODUCT_IMAGE_FOLDER_PATH = IMAGE_FOLDER_PATH + "/product";
    private static final String USER_IMAGE_FOLDER_PATH = IMAGE_FOLDER_PATH + "/user";

    private FileUtils() { }

    public static Path saveProductImage(MultipartFile imageFile) {
        return saveImage(imageFile, PRODUCT_IMAGE_FOLDER_PATH);
    }

    private static Path saveImage(MultipartFile imageFile, String productImageFolderPath) {
        if (imageFile == null) {
            throw new IllegalArgumentException("Image file can not be null!");
        }

        createDirectories(Paths.get(CATALOG, productImageFolderPath));

        Path savePath = Paths.get(productImageFolderPath, imageFile.getOriginalFilename());
        try {
            imageFile.transferTo(Paths.get(CATALOG, savePath.toString()));
        } catch (IOException e) {
            throw new RuntimeException("Can't save file by path=" + Paths.get(CATALOG, savePath.toString()));
        }
        return savePath;
    }

    public static Path saveUserImage(MultipartFile imageFile) {
        return saveImage(imageFile, USER_IMAGE_FOLDER_PATH);
    }

    private static void createDirectories(Path path) {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            log.info("Can't create directory");
            e.printStackTrace();
        }
    }
}
