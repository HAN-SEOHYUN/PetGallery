package com.example.wedlessInvite.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.wedlessInvite.dto.ImageListResponseDto;
import com.example.wedlessInvite.dto.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.example.wedlessInvite.common.Utils.getFileExtension;
import static com.example.wedlessInvite.common.VarConst.S3_UPLOAD_FOLDER;

@Service
@RequiredArgsConstructor
public class S3FileService {

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket.name}")
    private String bucket;

    public List<ImageListResponseDto> getS3FileList (String folder) throws IOException {
        System.out.println(amazonS3.listObjects(bucket,folder).getObjectSummaries());
        return amazonS3.listObjects(bucket, folder)
                .getObjectSummaries()
                .stream()
                .filter(obj -> !obj.getKey().endsWith("/")) // 디렉토리 제외
                .map(this::convertToDto) // S3ObjectSummary -> ImageListResponseDto 변환
                .collect(Collectors.toList());
    }

    public ImageUploadDto uploadS3 (MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        String uniqueFileName = S3_UPLOAD_FOLDER + makeUniqueFileName(originalFilename);
        //S3 업로드
        amazonS3.putObject(bucket, uniqueFileName, multipartFile.getInputStream(), metadata);
        //S3 URL
        String s3Url = amazonS3.getUrl(bucket, uniqueFileName).toString();
        String fileType = getFileExtension(multipartFile);
        String uploadedFileName = uniqueFileName.substring(uniqueFileName.lastIndexOf("/") + 1);

        ImageUploadDto imageUploadDto = ImageUploadDto.builder()
                .fileName(uploadedFileName)
                .orgFileName(multipartFile.getOriginalFilename())
                .fileSize(multipartFile.getSize())
                .s3Url(s3Url)
                .fileType(fileType)
                .build();

        return imageUploadDto;
    }

    private ImageListResponseDto convertToDto(S3ObjectSummary summary) {
        String key = summary.getKey();
        String orgFileName = key.substring(key.lastIndexOf("/") + 1); // 파일 이름 추출
        String s3Url = amazonS3.getUrl(bucket, key).toString(); // S3 URL 생성
        Long fileSize = summary.getSize(); // 파일 크기
        LocalDateTime modTime = LocalDateTime.ofInstant(summary.getLastModified().toInstant(), ZoneId.systemDefault()); // 수정 시간
        ImageListResponseDto imageListResponseDto = ImageListResponseDto.builder()
                .orgFileName(orgFileName)
                .s3Url(s3Url)
                .fileSize(fileSize)
                .modTime(modTime)
                .build();
        return imageListResponseDto;
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