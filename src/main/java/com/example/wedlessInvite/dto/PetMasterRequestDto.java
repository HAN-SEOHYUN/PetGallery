package com.example.wedlessInvite.dto;

import com.example.wedlessInvite.domain.Image.ImageUploads;
import com.example.wedlessInvite.domain.Pet.OwnerInfo;
import com.example.wedlessInvite.domain.Pet.PetDetailInfo;
import com.example.wedlessInvite.domain.Pet.PetMaster;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PetMasterRequestDto {
    @NotNull(message = "결혼일자는 필수 입력값입니다.")
    private LocalDate date;  // 결혼일자
    @NotNull
    private Long userId;
    private Long mainImageId; // 메인 이미지
    private List<Long> imageIdList; // 이미지 리스트
    private OwnerInfo ownerInfo;  // 신부 정보
    private PetDetailInfo petDetailInfo;  // 신랑 정보
    private String introText;  // 레터링 문구
    private String likeWord;  // 메인 텍스트
    private String hateWord;  // 인사말

    public PetMaster toEntity(ImageUploads mainImage) {
        return PetMaster.builder()
                .date(date)
                .mainImage(mainImage)
                .ownerInfo(ownerInfo)
                .petDetailInfo(petDetailInfo)
                .introText(introText)
                .likeWord(likeWord)
                .hateWord(hateWord)
                .build();
    }

    @Builder
    public PetMasterRequestDto(LocalDate date, ImageUploads mainImage, List<Long> imageIdList, OwnerInfo ownerInfo, PetDetailInfo petDetailInfo, String introText, String likeWord, String hateWord) {
        this.date = date;
        this.imageIdList = imageIdList;
        this.ownerInfo = ownerInfo;
        this.petDetailInfo = petDetailInfo;
        this.introText = introText;
        this.likeWord = likeWord;
        this.hateWord = hateWord;
    }

    @Override
    public String toString() {
        return "PetMasterRequestDto{" +
                "ownerInfo=" + (ownerInfo != null ? ownerInfo.toString() : "null") +
                ", petDetailInfo=" + (petDetailInfo != null ? petDetailInfo.toString() : "null") +
                ", letterTxt='" + introText + '\'' +
                ", mainTxt='" + likeWord + '\'' +
                ", greetTxt='" + hateWord + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
