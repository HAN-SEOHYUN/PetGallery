package com.example.wedlessInvite.controller;

import com.example.wedlessInvite.dto.ImageListResponseDto;
import com.example.wedlessInvite.dto.ImageUploadDto;
import com.example.wedlessInvite.service.ImageUploadService;
import com.example.wedlessInvite.service.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.example.wedlessInvite.common.VarConst.S3_UPLOAD_FOLDER;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class S3FileController {

    private final S3FileService s3FileService;
    private final ImageUploadService imageUploadService;

    @GetMapping("/get")
    public ResponseEntity<List<ImageListResponseDto>> getUploadedFileDetails(
            @RequestParam(required = false, defaultValue = S3_UPLOAD_FOLDER) String folder) throws IOException {

        List<ImageListResponseDto> fileDetails = s3FileService.getS3FileList(folder);

        if (fileDetails.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(fileDetails);
        }
        return ResponseEntity.ok(fileDetails);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            imageUploadService.validateFileSize(file);
            imageUploadService.validateFileExtension(file);

            ImageUploadDto dto = s3FileService.uploadS3(file);
            imageUploadService.saveFile(dto);

            return ResponseEntity.ok().body(dto.getS3Url());

        }  catch (IOException e) {
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }
    }
}
