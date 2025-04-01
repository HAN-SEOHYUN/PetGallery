package com.example.wedlessInvite.dto;

import com.example.wedlessInvite.domain.Image.ImageUploads;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ImageUploadDto {
    private String fileName;
    private String orgFileName;
    private String s3Url;
    private Long fileSize;
    private String fileType;
    private LocalDateTime regTime;
    private LocalDateTime modTime;


    public ImageUploads toEntity() {
        return ImageUploads.builder()
                .fileName(fileName)
                .orgFileName(orgFileName)
                .s3Url(s3Url)
                .fileSize(fileSize)
                .fileType(fileType)
                .isDeleted(false)
                .build();
    }

    public static ImageUploadDto fromEntity(ImageUploads imageUploads) {
        return ImageUploadDto.builder()
                .fileName(imageUploads.getFileName())
                .orgFileName(imageUploads.getOrgFileName())
                .s3Url(imageUploads.getS3Url())
                .fileSize(imageUploads.getFileSize())
                .fileType(imageUploads.getFileType())
                .build();
    }

    @Builder
    public ImageUploadDto(String fileName, String orgFileName, String s3Url, Long fileSize, String fileType) {
        this.fileName = fileName;
        this.orgFileName = orgFileName;
        this.s3Url = s3Url;
        this.fileSize = fileSize;
        this.fileType = fileType;
    }
}
