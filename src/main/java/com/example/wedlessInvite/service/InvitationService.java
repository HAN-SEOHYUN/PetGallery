package com.example.wedlessInvite.service;

import com.example.wedlessInvite.common.YN;
import com.example.wedlessInvite.common.logtrace.LogTrace;
import com.example.wedlessInvite.common.template.AbstractLogTraceTemplate;
import com.example.wedlessInvite.domain.Image.ImageUploads;
import com.example.wedlessInvite.domain.Image.ImageUploadsRepository;
import com.example.wedlessInvite.domain.Invitation.*;
import com.example.wedlessInvite.domain.Like.InvitationLikeRepository;
import com.example.wedlessInvite.domain.User.MasterUser;
import com.example.wedlessInvite.domain.User.MasterUserRepository;
import com.example.wedlessInvite.dto.ImageUploadDto;
import com.example.wedlessInvite.dto.InvitationMasterRequestDto;
import com.example.wedlessInvite.dto.InvitationMasterResponseDto;
import com.example.wedlessInvite.exception.CustomException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.wedlessInvite.exception.ErrorCode.POST_NOT_FOUND;
import static com.example.wedlessInvite.exception.ErrorCode.USER_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Service
public class InvitationService {

    private final InvitationMasterRepository invitationMasterRepository;
    private final BrideInfoRepository brideInfoRepository;
    private final GroomInfoRepository groomInfoRepository;
    private final MasterUserRepository userRepository;
    private final ImageUploadsRepository imageUploadsRepository;

    private final ImageUploadService imageUploadService;
    private final S3FileService s3FileService;

    private final LogTrace trace;
    private final InvitationLikeRepository invitationLikeRepository;

    @Transactional
    public InvitationMasterResponseDto saveInvitationMaster(InvitationMasterRequestDto dto) throws IOException {
        AbstractLogTraceTemplate<InvitationMasterResponseDto> template = new AbstractLogTraceTemplate<>(trace) {
            @Override
            protected InvitationMasterResponseDto call() throws IOException {

                //User 정보 검증
                MasterUser masterUser = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

                // 기타정보 저장
                BrideInfo brideInfo = brideInfoRepository.save(dto.getBrideInfo());
                GroomInfo groomInfo = groomInfoRepository.save(dto.getGroomInfo());
                ImageUploads imageUploads = imageUploadsRepository.findImageUploadsById(dto.getMainImageId());

                // 엔티티 저장
                InvitationMaster invitationMaster = dto.toEntity(imageUploads);
                invitationMaster.setAccessKey();
                invitationMaster.setMasterUser(masterUser);
                InvitationMaster invitation = invitationMasterRepository.save(invitationMaster);

                // 이미지 리스트 처리
                System.out.println(dto.getImageIdList());
                if (dto.getImageIdList() != null && !dto.getImageIdList().isEmpty()) {
                    List<ImageUploads> images = imageUploadsRepository.findAllById(dto.getImageIdList());
                    images.forEach(image -> image.setInvitationId(invitation));
                    imageUploadsRepository.saveAll(images);
                }

                // DTO 변환 후 반환
                return InvitationMasterResponseDto.fromEntity(invitation);
            }
        };

        // template.execute()가 InvitationMasterResponseDto를 반환하도록 처리
        return template.execute("InvitationService.saveInvitationMaster");
    }

    private ImageUploadDto validateAndUploadS3(MultipartFile file) throws IOException {
        imageUploadService.validateFileSize(file);
        imageUploadService.validateFileExtension(file);
        return s3FileService.uploadS3(file);
    }

    public Page<InvitationMasterResponseDto> getAllInvitations(Pageable pageable) {
        // InvitationMaster 엔티티를 페이지네이션으로 조회
        Page<InvitationMaster> entity = invitationMasterRepository.findByDeleteYNOrderByRegTimeDesc("N", pageable);

        // Entity에서 DTO로 변환하여 반환
        return entity.map(invitation -> InvitationMasterResponseDto.builder()
                .id(invitation.getId())
                .date(invitation.getDate())
//                .brideInfo(invitation.getBrideInfo())
//                .groomInfo(invitation.getGroomInfo())
                .mainImage(invitation.getMainImage())
                .letterTxt(invitation.getLetterTxt())
                .mainTxt(invitation.getMainTxt())
                .greetTxt(invitation.getGreetTxt())
                .regTime(invitation.getRegTime())
                .likeCount(invitationLikeRepository.countByInvitationMasterId(invitation.getId()))
                .build());
    }

    public InvitationMasterResponseDto getInvitationDetail(Long id) {
        InvitationMaster entity = invitationMasterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invitation not found for ID: " + id));

        return InvitationMasterResponseDto.builder()
                .id(entity.getId())
                .brideInfo(entity.getBrideInfo())
                .groomInfo(entity.getGroomInfo())
                .mainImage(entity.getMainImage())
                .imageList(entity.getImageList().stream()
                        .map(ImageUploadDto::fromEntity)
                        .collect(Collectors.toList()))
                .date(entity.getDate())
                .letterTxt(entity.getLetterTxt())
                .mainTxt(entity.getMainTxt())
                .greetTxt(entity.getGreetTxt())
                .likeCount(invitationLikeRepository.countByInvitationMasterId(entity.getId()))
                .build();
    }

    public void deleteInvitation(Long id) {
        InvitationMaster entity = invitationMasterRepository.findById(id)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        entity.setDeleted(String.valueOf(YN.Y));
        invitationMasterRepository.save(entity);
    }
}
