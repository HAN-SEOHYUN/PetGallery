package com.example.wedlessInvite.service;

import com.example.wedlessInvite.common.YN;
import com.example.wedlessInvite.common.logtrace.LogTrace;
import com.example.wedlessInvite.common.template.AbstractLogTraceTemplate;
import com.example.wedlessInvite.domain.Image.ImageUploads;
import com.example.wedlessInvite.domain.Image.ImageUploadsRepository;
import com.example.wedlessInvite.domain.Like.PetLikeRepository;
import com.example.wedlessInvite.domain.Pet.*;
import com.example.wedlessInvite.domain.User.UserMaster;
import com.example.wedlessInvite.domain.User.UserMasterRepository;
import com.example.wedlessInvite.dto.ImageUploadDto;
import com.example.wedlessInvite.dto.PetMasterRequestDto;
import com.example.wedlessInvite.dto.PetMasterResponseDto;
import com.example.wedlessInvite.dto.UserMasterResponseDto;
import com.example.wedlessInvite.exception.CustomException;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.wedlessInvite.exception.ErrorCode.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PetMasterService {

    private final PetMasterRepository petMasterRepository;
    private final OwnerInfoRepository ownerInfoRepository;
    private final PetDetailInfoRepository petDetailInfoRepository;
    private final UserMasterRepository userMasterRepository;
    private final ImageUploadsRepository imageUploadsRepository;

    private final ImageUploadService imageUploadService;
    private final S3FileService s3FileService;

    private final LogTrace trace;
    private final PetLikeRepository petLikeRepository;

    @Transactional
    public PetMasterResponseDto saveInvitationMaster(PetMasterRequestDto request) throws IOException {
        AbstractLogTraceTemplate<PetMasterResponseDto> template = new AbstractLogTraceTemplate<>(trace) {
            @Override
            protected PetMasterResponseDto call() throws IOException {

                //User 정보 검증
                UserMaster userMaster = userMasterRepository.findById(request.getUserId())
                        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

                // 기타정보 저장
                OwnerInfo ownerInfo = ownerInfoRepository.save(request.getOwnerInfo());
                PetDetailInfo petDetailInfo = petDetailInfoRepository.save(request.getPetDetailInfo());
                ImageUploads imageUploads = imageUploadsRepository.findImageUploadsById(request.getMainImageId());

                // 엔티티 저장
                PetMaster petMaster = request.toEntity(imageUploads);
                petMaster.setUserMaster(userMaster);
                PetMaster entity = petMasterRepository.save(petMaster);

                // 이미지 리스트 처리
                System.out.println(request.getImageIdList());
                if (request.getImageIdList() != null && !request.getImageIdList().isEmpty()) {
                    List<ImageUploads> images = imageUploadsRepository.findAllById(request.getImageIdList());
                    images.forEach(image -> image.setPetId(entity));
                    imageUploadsRepository.saveAll(images);
                }

                // DTO 변환 후 반환
                return PetMasterResponseDto.fromEntity(entity);
            }
        };

        // template.execute()가 InvitationMasterResponseDto를 반환하도록 처리
        return template.execute("PetMasterService.saveInvitationMaster");
    }

    private ImageUploadDto validateAndUploadS3(MultipartFile file) throws IOException {
        imageUploadService.validateFileSize(file);
        imageUploadService.validateFileExtension(file);
        return s3FileService.uploadS3(file);
    }

    public Page<PetMasterResponseDto> getAllInvitations(Pageable pageable, Long userId) {
        Page<PetMaster> entity = petMasterRepository.findByDeleteYNOrderByRegTimeDesc("N", pageable);

        return entity.map(pet -> {
            boolean isLiked = petLikeRepository.existsByPetMasterIdAndUserMasterId(pet.getId(), userId);

            return PetMasterResponseDto.builder()
                    .id(pet.getId())
                    .date(pet.getDate())
                    .mainImage(pet.getMainImage())
                    .introText(pet.getIntroText())
                    .likeWord(pet.getLikeWord())
                    .hateWord(pet.getHateWord())
                    .regTime(pet.getRegTime())
                    .accessKey(pet.getAccessKey())
                    .name(pet.getName())
                    .likeCount(petLikeRepository.countByPetMasterId(pet.getId()))
                    .liked(isLiked) // 추가 필드
                    .build();
        });
    }

    public PetMasterResponseDto getInvitationDetail(String accessKey, HttpSession session) {
        PetMaster entity = petMasterRepository.findByAccessKey(accessKey)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        UserMasterResponseDto userMasterDto = (UserMasterResponseDto) session.getAttribute("userMaster");
        if (userMasterDto == null) {
            throw new CustomException(INVALID_ACCESS);
        }

        Long userId = userMasterDto.getId();
        boolean hasDeletePermission = userId != null && userId.equals(entity.getUserMaster().getId());

        return PetMasterResponseDto.builder()
                .id(entity.getId())
//                .ownerInfo(entity.getOwnerInfo())
//                .petDetailInfo(entity.getPetDetailInfo())
                .mainImage(entity.getMainImage())
                .imageList(entity.getImageList().stream()
                        .map(ImageUploadDto::fromEntity)
                        .collect(Collectors.toList()))
                .date(entity.getDate())
                .name(entity.getName())
                .introText(entity.getIntroText())
                .likeWord(entity.getLikeWord())
                .hateWord(entity.getHateWord())
                .regTime(entity.getRegTime())
                .hasDeletePermission(hasDeletePermission)
                .likeCount(petLikeRepository.countByPetMasterId(entity.getId()))
                .build();
    }

    public void deleteInvitation(Long id, HttpSession session) {
        PetMaster entity = petMasterRepository.findById(id)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        UserMasterResponseDto userMasterDto = (UserMasterResponseDto) session.getAttribute("userMaster");

        // 세션에서 로그인 사용자 확인
        if (userMasterDto == null) {
            throw new CustomException(LOGIN_REQUIRED); // 401
        }

        // 작성자와 현재 로그인 사용자가 같은지 검증
        if (!userMasterDto.getId().equals(entity.getUserMaster().getId())) {
            throw new CustomException(INVALID_ACCESS); // 403
        }

        entity.setDeleted(String.valueOf(YN.Y));
        petMasterRepository.save(entity);
    }
}
