package com.example.wedlessInvite.service;

import com.example.wedlessInvite.common.YN;
import com.example.wedlessInvite.common.logtrace.LogTrace;
import com.example.wedlessInvite.common.template.AbstractLogTraceTemplate;
import com.example.wedlessInvite.domain.Image.ImageUploads;
import com.example.wedlessInvite.domain.Image.ImageUploadsRepository;
import com.example.wedlessInvite.domain.Invitation.*;
import com.example.wedlessInvite.dto.ImageUploadDto;
import com.example.wedlessInvite.dto.InvitationMasterRequestDto;
import com.example.wedlessInvite.dto.InvitationMasterResponseDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@RequiredArgsConstructor
@Service
public class InvitationService {

    private final InvitationMasterRepository invitationMasterRepository;

    private final BrideInfoRepository brideInfoRepository;

    private final GroomInfoRepository groomInfoRepository;

    private final ImageUploadService imageUploadService;

    private final S3FileService s3FileService;
    private final ImageUploadsRepository imageUploadsRepository;
    private final LogTrace trace;

    @Transactional
    public InvitationMaster saveInvitationMaster(InvitationMasterRequestDto dto) throws IOException {
        AbstractLogTraceTemplate<InvitationMaster> template = new AbstractLogTraceTemplate<>(trace) {
            @Override
            protected InvitationMaster call() throws IOException {
                // 비즈니스 로직 수행
                BrideInfo brideInfo = brideInfoRepository.save(dto.getBrideInfo());
                GroomInfo groomInfo = groomInfoRepository.save(dto.getGroomInfo());
                ImageUploads imageUploads = imageUploadsRepository.findImageUploadsById(dto.getMainImageId());
                dto.setMainImage(imageUploads);

                // 엔티티 저장
                return invitationMasterRepository.save(dto.toEntity());
            }
        };
        // 예외 처리 및 실행
        return template.execute("InvitationService.saveInvitationMaster");
    }

    private ImageUploadDto validateAndUploadS3(MultipartFile file) throws IOException {
        imageUploadService.validateFileSize(file);
        imageUploadService.validateFileExtension(file);
        return s3FileService.uploadS3(file);
    }

    public Page<InvitationMasterResponseDto> getAllInvitations(Pageable pageable) {
        Page<InvitationMaster> entity = invitationMasterRepository.findByDeleteYN("N",pageable);

        // Entity에서 DTO로 변환
        return entity.map(invitation -> InvitationMasterResponseDto.builder()
                .id(invitation.getId())
                .date(invitation.getDate())
                .brideInfo(invitation.getBrideInfo())
                .groomInfo(invitation.getGroomInfo())
                .mainImage(invitation.getMainImage())
                .letterTxt(invitation.getLetterTxt())
                .mainTxt(invitation.getMainTxt())
                .greetTxt(invitation.getGreetTxt())
                .build());
    }

    public InvitationMasterResponseDto getInvitationDetail(Long id) {
        InvitationMaster entity = invitationMasterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invitation not found for ID: " + id));

        return InvitationMasterResponseDto.builder()
                .id(entity.getId())
                .brideInfo(entity.getBrideInfo())
                .groomInfo(entity.getGroomInfo())
                .mainImage(entity.getMainImage())
                .date(entity.getDate())
                .letterTxt(entity.getLetterTxt())
                .mainTxt(entity.getMainTxt())
                .greetTxt(entity.getGreetTxt())
                .build();
    }

    public void deleteInvitation(Long id) {
        InvitationMaster entity = invitationMasterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        entity.setDeleted(String.valueOf(YN.Y));
        invitationMasterRepository.save(entity);
    }
}
