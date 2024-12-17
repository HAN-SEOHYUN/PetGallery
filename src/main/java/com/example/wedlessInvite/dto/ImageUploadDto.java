package com.example.wedlessInvite.dto;

import com.example.wedlessInvite.domain.Post.ImageUploads;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImageUploadDto {
    private String fileName;
    private String orgFileName;
    private String s3Url;
    private Long fileSize;

    public ImageUploads toEntity() {
        return ImageUploads.builder()
                .fileName(fileName)
                .orgFileName(orgFileName)
                .s3Url(s3Url)
                .fileSize(fileSize)
                .build();
    }

    @Builder
    public ImageUploadDto(String fileName, String orgFileName, String s3Url, Long fileSize) {
        this.fileName = fileName;
        this.orgFileName = orgFileName;
        this.s3Url = s3Url;
        this.fileSize = fileSize;
    }
}
