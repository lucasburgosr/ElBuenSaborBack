package com.example.buensaborback.business.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Map;
import java.util.UUID;


public interface ImageService {


    ResponseEntity<List<Map<String, Object>>> getAllImages();
    ResponseEntity<String> uploadImages(MultipartFile[] files);
    ResponseEntity<String> deleteImage(String publicId, UUID uuid);


}

