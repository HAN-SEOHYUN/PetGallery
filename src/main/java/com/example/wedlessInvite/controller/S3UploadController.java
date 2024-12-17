package com.example.wedlessInvite.controller;

import com.example.wedlessInvite.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class S3UploadController {

    private final S3UploadService s3UploadService;

    @PostMapping("/s3/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // 파일을 S3에 저장하고 URL을 반환
            String fileUrl = s3UploadService.uploadS3(file);
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            // 예외 발생 시 500 에러 응답
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }
    }
}
