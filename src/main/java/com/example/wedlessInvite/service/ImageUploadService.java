package com.example.wedlessInvite.service;

import com.example.wedlessInvite.domain.Post.ImageUploads;
import com.example.wedlessInvite.domain.Post.ImageUploadsRepository;
import com.example.wedlessInvite.dto.ImageUploadDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.example.wedlessInvite.common.Utils.getFileExtension;
import static com.example.wedlessInvite.common.VarConst.MAX_FILE_SIZE;
import static com.example.wedlessInvite.common.VarConst.VALID_FILE_EXTENSIONS;

@RequiredArgsConstructor
@Service
public class ImageUploadService {
    private final ImageUploadsRepository imageUploadsRepository;

    @Transactional
    public ImageUploads saveFile (ImageUploadDto imageUploadDto) {
        return imageUploadsRepository.save(imageUploadDto.toEntity());
    }

    public void validateFileSize(MultipartFile file) {
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File size exceeds the maximum limit of 1MB.");
        }
    }

    public void validateFileExtension(MultipartFile file) {
        if (!isValidExtension(file)) {
            throw new IllegalArgumentException("Invalid file type. Only PNG, JPG, and JPEG are allowed.");
        }
    }

    private boolean isValidExtension(MultipartFile file) {
        String fileExtension = getFileExtension(file);
        return VALID_FILE_EXTENSIONS.contains(fileExtension);
    }
}
