package com.example.wedlessInvite.service;

import com.example.wedlessInvite.domain.Post.ImageUploads;
import com.example.wedlessInvite.domain.Post.ImageUploadsRepository;
import com.example.wedlessInvite.dto.ImageUploadDto;
import com.example.wedlessInvite.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.example.wedlessInvite.common.Utils.getFileExtension;
import static com.example.wedlessInvite.common.VarConst.MAX_FILE_SIZE;
import static com.example.wedlessInvite.common.VarConst.VALID_FILE_EXTENSIONS;
import static com.example.wedlessInvite.exception.ErrorCode.EXCEED_MAX_FILE_SIZE;
import static com.example.wedlessInvite.exception.ErrorCode.INVALID_FILE_TYPE;

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
            throw new CustomException(EXCEED_MAX_FILE_SIZE);
        }
    }

    public void validateFileExtension(MultipartFile file) {
        if (!isValidExtension(file)) {
            throw new CustomException(INVALID_FILE_TYPE);
        }
    }

    private boolean isValidExtension(MultipartFile file) {
        String fileExtension = getFileExtension(file);
        return VALID_FILE_EXTENSIONS.contains(fileExtension);
    }
}
