package com.example.wedlessInvite.service;

import com.example.wedlessInvite.domain.Post.ImageUploads;
import com.example.wedlessInvite.domain.Post.ImageUploadsRepository;
import com.example.wedlessInvite.dto.ImageUploadDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class ImageUploadService {
    private final ImageUploadsRepository imageUploadsRepository;

    @Transactional
    public ImageUploads saveFile (ImageUploadDto imageUploadDto) {
        return imageUploadsRepository.save(imageUploadDto.toEntity());
    }
}
