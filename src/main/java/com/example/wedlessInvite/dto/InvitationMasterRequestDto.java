package com.example.wedlessInvite.dto;

import com.example.wedlessInvite.domain.Image.ImageUploads;
import com.example.wedlessInvite.domain.Invitation.OwnerInfo;
import com.example.wedlessInvite.domain.Invitation.GroomInfo;
import com.example.wedlessInvite.domain.Invitation.InvitationMaster;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class InvitationMasterRequestDto {
    @NotNull(message = "결혼일자는 필수 입력값입니다.")
    private LocalDate date;  // 결혼일자
    @NotNull
    private Long userId;
    private Long mainImageId; // 메인 이미지
    private List<Long> imageIdList; // 이미지 리스트
    private OwnerInfo ownerInfo;  // 신부 정보
    private GroomInfo groomInfo;  // 신랑 정보
    private String letterTxt;  // 레터링 문구
    private String mainTxt;  // 메인 텍스트
    private String greetTxt;  // 인사말
    private String accessKey;

    public InvitationMaster toEntity(ImageUploads mainImage) {
        return InvitationMaster.builder()
                .date(date)
                .mainImage(mainImage)
                .ownerInfo(ownerInfo)
                .groomInfo(groomInfo)
                .letterTxt(letterTxt)
                .mainTxt(mainTxt)
                .greetTxt(greetTxt)
                .accessKey(null)
                .build();
    }

    @Builder
    public InvitationMasterRequestDto(LocalDate date, ImageUploads mainImage, List<Long> imageIdList, OwnerInfo ownerInfo, GroomInfo groomInfo, String letterTxt, String mainTxt, String greetTxt) {
        this.date = date;
        this.imageIdList = imageIdList;
        this.ownerInfo = ownerInfo;
        this.groomInfo = groomInfo;
        this.letterTxt = letterTxt;
        this.mainTxt = mainTxt;
        this.greetTxt = greetTxt;
    }

    @Override
    public String toString() {
        return "InvitationMasterRequestDto{" +
                "ownerInfo=" + (ownerInfo != null ? ownerInfo.toString() : "null") +
                ", groomInfo=" + (groomInfo != null ? groomInfo.toString() : "null") +
                ", letterTxt='" + letterTxt + '\'' +
                ", mainTxt='" + mainTxt + '\'' +
                ", greetTxt='" + greetTxt + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
