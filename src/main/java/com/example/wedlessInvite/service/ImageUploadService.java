package com.example.wedlessInvite.service;

import com.example.wedlessInvite.domain.Image.ImageUploads;
import com.example.wedlessInvite.domain.Image.ImageUploadsRepository;
import com.example.wedlessInvite.dto.ImageUploadDto;
import com.example.wedlessInvite.exception.CustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.Map;

import static com.example.wedlessInvite.common.Utils.getFileExtension;
import static com.example.wedlessInvite.common.VarConst.*;
import static com.example.wedlessInvite.exception.ErrorCode.*;

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

    private final RestTemplate restTemplate = new RestTemplate();

    private void isValidatePetImage(String result) {
        if(!Boolean.parseBoolean(result)) {
            throw new CustomException(INVALID_PET_IMAGE);
        }
    }

    public void validatePetImage(MultipartFile file) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA); // MULTIPART_FORM_DATA로 설정

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", file.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                DOG_CAT_API_URL, HttpMethod.POST, requestEntity, String.class);

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);

        isValidatePetImage(responseMap.get("result").toString());
    }
}
