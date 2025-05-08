package com.example.wedlessInvite.dto;

import com.example.wedlessInvite.domain.Image.ImageUploads;
import com.example.wedlessInvite.domain.Pet.OwnerInfo;
import com.example.wedlessInvite.domain.Pet.GroomInfo;
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
    private GroomInfo groomInfo;
    private ImageUploads mainImage;
    private List<ImageUploadDto> imageList;
    private String letterTxt;
    private String mainTxt;
    private String greetTxt;
    private LocalDateTime regTime;
    private int likeCount;
    private String accessKey;

    @Builder
    public PetMasterResponseDto(Long id, LocalDate date, OwnerInfo ownerInfo, GroomInfo groomInfo, ImageUploads mainImage, List<ImageUploadDto> imageList, String letterTxt, String mainTxt, String greetTxt, LocalDateTime regTime, int likeCount, String accessKey) {
        this.id = id;
        this.date = date;
        this.ownerInfo = ownerInfo;
        this.groomInfo = groomInfo;
        this.mainImage = mainImage;
        this.imageList = imageList;
        this.letterTxt = letterTxt;
        this.mainTxt = mainTxt;
        this.greetTxt = greetTxt;
        this.regTime = regTime;
        this.likeCount = likeCount;
        this.accessKey = accessKey;
    }

    public static PetMasterResponseDto fromEntity(PetMaster petMaster) {
        return PetMasterResponseDto.builder()
                .id(petMaster.getId())
                .date(petMaster.getDate())
                .ownerInfo(petMaster.getOwnerInfo())
                .groomInfo(petMaster.getGroomInfo())
                .mainImage(petMaster.getMainImage())  // mainImage는 ImageUploads 타입 그대로
                .imageList(petMaster.getImageList() != null ? petMaster.getImageList().stream()
                        .map(ImageUploadDto::fromEntity)  // ImageUploads -> ImageUploadDto 변환
                        .collect(Collectors.toList()) : new ArrayList<>())  // imageList가 null일 경우 빈 리스트
                .letterTxt(petMaster.getLetterTxt())
                .mainTxt(petMaster.getMainTxt())
                .greetTxt(petMaster.getGreetTxt())
                .regTime(petMaster.getRegTime() != null
                        ? petMaster.getRegTime()
                        : null)
                .build();
    }

}
