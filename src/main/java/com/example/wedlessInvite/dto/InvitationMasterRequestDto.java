package com.example.wedlessInvite.dto;

import com.example.wedlessInvite.domain.Image.ImageUploads;
import com.example.wedlessInvite.domain.Invitation.BrideInfo;
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
    private ImageUploads mainImage;
    private Long mainImageId; // 이미지 ID
    private List<Long> imageIdList;
    private BrideInfo brideInfo;  // 신부 정보
    private GroomInfo groomInfo;  // 신랑 정보
    private String letterTxt;  // 레터링 문구
    private String mainTxt;  // 메인 텍스트
    private String greetTxt;  // 인사말

    public InvitationMaster toEntity() {
        return InvitationMaster.builder()
                .mainImage(mainImage)
                .date(date)
                .brideInfo(brideInfo)
                .groomInfo(groomInfo)
                .letterTxt(letterTxt)
                .mainTxt(mainTxt)
                .greetTxt(greetTxt)
                .build();
    }

    @Builder
    public InvitationMasterRequestDto(LocalDate date, ImageUploads mainImage,Long mainImageId, List<Long> imageIdList,BrideInfo brideInfo, GroomInfo groomInfo, String letterTxt, String mainTxt, String greetTxt) {
        this.date = date;
        this.mainImage = mainImage;
        this.mainImageId = mainImageId;
        this.imageIdList = imageIdList;
        this.brideInfo = brideInfo;
        this.groomInfo = groomInfo;
        this.letterTxt = letterTxt;
        this.mainTxt = mainTxt;
        this.greetTxt = greetTxt;
    }

    @Override
    public String toString() {
        return "InvitationMasterRequestDto{" +
                "brideInfo=" + (brideInfo != null ? brideInfo.toString() : "null") +
                ", groomInfo=" + (groomInfo != null ? groomInfo.toString() : "null") +
                ", letterTxt='" + letterTxt + '\'' +
                ", mainTxt='" + mainTxt + '\'' +
                ", greetTxt='" + greetTxt + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
