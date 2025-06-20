package com.example.wedlessInvite.controller.api;

import com.example.wedlessInvite.domain.Image.ImageUploads;
import com.example.wedlessInvite.dto.ImageListResponseDto;
import com.example.wedlessInvite.dto.ImageUploadDto;
import com.example.wedlessInvite.service.ImageUploadService;
import com.example.wedlessInvite.service.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.example.wedlessInvite.common.VarConst.S3_UPLOAD_FOLDER;
import static com.example.wedlessInvite.exception.ErrorCode.EXCEED_MAX_FILE_SIZE;
import static com.example.wedlessInvite.exception.ErrorCode.INVALID_FILE_TYPE;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final S3FileService s3FileService;
    private final ImageUploadService imageUploadService;

    @GetMapping
    public ResponseEntity<List<ImageListResponseDto>> getImages(
            @RequestParam(required = false, defaultValue = S3_UPLOAD_FOLDER) String folder) throws IOException {

        List<ImageListResponseDto> fileDetails = s3FileService.getS3FileList(folder);

        if (fileDetails.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(fileDetails);
        }
        return ResponseEntity.ok(fileDetails);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> createImage(@RequestPart("file") MultipartFile file) {
        try {
            imageUploadService.validatePetImage(file);
            imageUploadService.validateFileSize(file);
            imageUploadService.validateFileExtension(file);

            ImageUploadDto dto = s3FileService.uploadS3(file);
            ImageUploads imageUploads = imageUploadService.saveFile(dto);

            Long imageID =  imageUploads.getId();
            return ResponseEntity.ok().body(imageID);
        }  catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
