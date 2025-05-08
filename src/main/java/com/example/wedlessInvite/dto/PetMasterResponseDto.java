package com.example.wedlessInvite.dto;

import com.example.wedlessInvite.domain.Image.ImageUploads;
import com.example.wedlessInvite.domain.Pet.OwnerInfo;
import com.example.wedlessInvite.domain.Pet.PetDetailInfo;
import com.example.wedlessInvite.domain.Pet.PetMaster;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PetMasterResponseDto {
    private Long id;
    private LocalDate date;
    private OwnerInfo ownerInfo;
    private PetDetailInfo petDetailInfo;
    private ImageUploads mainImage;
    private List<ImageUploadDto> imageList;
    private String introText;
    private String likeWord;
    private String hateWord;
    private LocalDateTime regTime;
    private int likeCount;
    private String accessKey;
    private String name;

    @Builder
    public PetMasterResponseDto(Long id, LocalDate date, OwnerInfo ownerInfo, PetDetailInfo petDetailInfo, ImageUploads mainImage, List<ImageUploadDto> imageList, String introText, String likeWord, String hateWord, LocalDateTime regTime, int likeCount, String accessKey, String name) {
        this.id = id;
        this.date = date;
        this.ownerInfo = ownerInfo;
        this.petDetailInfo = petDetailInfo;
        this.mainImage = mainImage;
        this.imageList = imageList;
        this.introText = introText;
        this.likeWord = likeWord;
        this.hateWord = hateWord;
        this.regTime = regTime;
        this.likeCount = likeCount;
        this.accessKey = accessKey;
        this.name = name;
    }

    public static PetMasterResponseDto fromEntity(PetMaster petMaster) {
        return PetMasterResponseDto.builder()
                .id(petMaster.getId())
                .date(petMaster.getDate())
                .name(petMaster.getName())
                .ownerInfo(petMaster.getOwnerInfo())
                .petDetailInfo(petMaster.getPetDetailInfo())
                .mainImage(petMaster.getMainImage())  // mainImage는 ImageUploads 타입 그대로
                .imageList(petMaster.getImageList() != null ? petMaster.getImageList().stream()
                        .map(ImageUploadDto::fromEntity)  // ImageUploads -> ImageUploadDto 변환
                        .collect(Collectors.toList()) : new ArrayList<>())  // imageList가 null일 경우 빈 리스트
                .introText(petMaster.getIntroText())
                .likeWord(petMaster.getLikeWord())
                .hateWord(petMaster.getHateWord())
                .regTime(petMaster.getRegTime() != null
                        ? petMaster.getRegTime()
                        : null)
                .build();
    }

}
