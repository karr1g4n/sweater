package com.example.sweater.sevice.impl;

import com.example.sweater.model.Message;
import com.example.sweater.sevice.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {


    @Value("${upload.path}")
    private String uploadPath;


    @Override
    public void saveFile(MultipartFile file, Message message) throws IOException {
        if (!file.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultName));
            message.setFilename(resultName);
        }

    }
}
