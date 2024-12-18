package com.example.wedlessInvite.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ImageListResponseDto {
    private String orgFileName;
    private String s3Url;
    private Long fileSize;
    private LocalDateTime modTime;

    @Builder
    public ImageListResponseDto(String orgFileName, String s3Url, Long fileSize, LocalDateTime modTime) {
        this.orgFileName = orgFileName;
        this.s3Url = s3Url;
        this.fileSize = fileSize;
        this.modTime = modTime;
    }
}
