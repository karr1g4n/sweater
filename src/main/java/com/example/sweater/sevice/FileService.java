package com.example.sweater.sevice;

import com.example.sweater.model.Message;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    public void saveFile(MultipartFile file, Message message) throws IOException;
}
