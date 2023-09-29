package com.example.demo.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    String saveFile(MultipartFile file);
    byte[] downloadFile(String filename);
    String deleteFile(String filename);
    List<String> listAllFiles();
}
