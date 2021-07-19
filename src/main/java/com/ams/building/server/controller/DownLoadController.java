package com.ams.building.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
public class DownLoadController {

    public static String UPLOAD_FOLDER = "F:\\abc\\";

    @GetMapping(value = "/download") // avatar=?
    public void download(HttpServletResponse response, @RequestParam("image") String image) {
        File file = new File(UPLOAD_FOLDER + File.separator + image);
        if (file.exists()) {
            try {
                Files.copy(file.toPath(), response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
