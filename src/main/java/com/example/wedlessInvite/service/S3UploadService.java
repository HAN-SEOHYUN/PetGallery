package com.example.wedlessInvite.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.wedlessInvite.dto.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3UploadService {

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket.name}")
    private String bucket;

    public ImageUploadDto uploadS3 (MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        String uniqueFileName = "uploads/" + makeUniqueFileName(originalFilename);
        //S3 업로드
        amazonS3.putObject(bucket, uniqueFileName, multipartFile.getInputStream(), metadata);

        //S3 URL 생성
        String s3Url = amazonS3.getUrl(bucket, uniqueFileName).toString();

        //Build imageUploadDto
        ImageUploadDto imageUploadDto = ImageUploadDto.builder()
                .fileName(multipartFile.getName())
                .orgFileName(multipartFile.getOriginalFilename())
                .fileSize(multipartFile.getSize())
                .s3Url(s3Url)
                .build();

        return imageUploadDto;
    }

    private String makeUniqueFileName(String originalFilename) {

        // 파일 확장자 추출
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String baseFilename = originalFilename != null ? originalFilename.substring(0, originalFilename.lastIndexOf(".")) : "unknown";
        String uuid = UUID.randomUUID().toString();

        // UUID 기반의 새로운 파일 이름 생성
        return String.format("%s_%s%s",baseFilename, uuid, extension);
    }
}