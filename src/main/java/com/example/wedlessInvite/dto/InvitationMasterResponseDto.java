package com.example.wedlessInvite.dto;

import com.example.wedlessInvite.domain.Image.ImageUploads;
import com.example.wedlessInvite.domain.Invitation.BrideInfo;
import com.example.wedlessInvite.domain.Invitation.GroomInfo;
import com.example.wedlessInvite.domain.Invitation.InvitationMaster;
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
public class InvitationMasterResponseDto {
    private Long id;
    private LocalDate date;
    private BrideInfo brideInfo;
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
    public InvitationMasterResponseDto(Long id, LocalDate date, BrideInfo brideInfo, GroomInfo groomInfo,ImageUploads mainImage, List<ImageUploadDto> imageList, String letterTxt, String mainTxt, String greetTxt, LocalDateTime regTime, int likeCount, String accessKey) {
        this.id = id;
        this.date = date;
        this.brideInfo = brideInfo;
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

    public static InvitationMasterResponseDto fromEntity(InvitationMaster invitationMaster) {
        return InvitationMasterResponseDto.builder()
                .id(invitationMaster.getId())
                .date(invitationMaster.getDate())
                .brideInfo(invitationMaster.getBrideInfo())
                .groomInfo(invitationMaster.getGroomInfo())
                .mainImage(invitationMaster.getMainImage())  // mainImage는 ImageUploads 타입 그대로
                .imageList(invitationMaster.getImageList() != null ? invitationMaster.getImageList().stream()
                        .map(ImageUploadDto::fromEntity)  // ImageUploads -> ImageUploadDto 변환
                        .collect(Collectors.toList()) : new ArrayList<>())  // imageList가 null일 경우 빈 리스트
                .letterTxt(invitationMaster.getLetterTxt())
                .mainTxt(invitationMaster.getMainTxt())
                .greetTxt(invitationMaster.getGreetTxt())
                .regTime(invitationMaster.getRegTime() != null
                        ? invitationMaster.getRegTime()
                        : null)
                .build();
    }

}
