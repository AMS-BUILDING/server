package com.ams.building.server.utils;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileStore {

    public static String UPLOAD_FOLDER = "C:\\image\\";

    public static List<String> getFilePaths(List<MultipartFile> multipartFiles, String prefix) {
        List<String> images = new ArrayList<String>();
        if (multipartFiles != null) {
            for (int i = 0; i < multipartFiles.size(); i++) {
                MultipartFile imageFile = multipartFiles.get(i);
                if (imageFile != null && !imageFile.isEmpty()) {
                    try {
                        int index = imageFile.getOriginalFilename().lastIndexOf(".");
                        String ext = imageFile.getOriginalFilename().substring(index);
                        String image = prefix + System.currentTimeMillis() + "-" + i + ext;

                        Path pathAvatar = Paths.get(UPLOAD_FOLDER + File.separator + image);
                        Files.write(pathAvatar, imageFile.getBytes());

                        images.add(image);
                    } catch (IOException e) {
                        throw new RuntimeException();

                    }
                }
            }

        }
        return images;
    }

    public static String getDefaultAvatar() {
        try {
            String fileName = "avatar" + RandomStringUtils.random(10, true, true);
            BufferedImage image = ImageIO.read(new File(UPLOAD_FOLDER + "avatar_default.png"));
            ImageIO.write(image, "png", new File(UPLOAD_FOLDER + fileName + ".png"));
            return fileName + ".png";
        } catch (Exception e) {
            return null;
        }
    }

    public static String getFilePath(MultipartFile multipartFile, String prefix) {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            try {
                int index = multipartFile.getOriginalFilename().lastIndexOf(".");
                String ext = multipartFile.getOriginalFilename().substring(index);
                String image = prefix + System.currentTimeMillis() + ext;

                Path pathImage = Paths.get(UPLOAD_FOLDER + File.separator + image);
                Files.write(pathImage, multipartFile.getBytes());

                return image;
            } catch (IOException e) {
            }
        }
        return null;
    }

    public static void deleteFiles(List<String> filePaths) {
        if (filePaths != null) {
            filePaths.forEach(image -> {
                try {
                    File avatarFile = new File(UPLOAD_FOLDER + File.separator + image);
                    if (avatarFile.exists()) {
                        avatarFile.delete();
                    }
                } catch (Exception e) {
                }
            });
        }
    }

    public static void deleteFile(String filePath) {
        if (filePath != null) {
            try {
                File avatarFile = new File(UPLOAD_FOLDER + File.separator + filePath);
                if (avatarFile.exists()) {
                    avatarFile.delete();
                }
            } catch (Exception e) {
            }
        }
    }

}
