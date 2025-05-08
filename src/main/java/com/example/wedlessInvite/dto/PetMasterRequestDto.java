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
    @NotNull(message = "반려동물 생일은 필수입력 값입니다.")
    private LocalDate date;  // 결혼일자
    @NotNull
    private Long userId;
    private Long mainImageId;
    private List<Long> imageIdList;
    private OwnerInfo ownerInfo;
    private PetDetailInfo petDetailInfo;
    private String introText;
    private String likeWord;
    private String hateWord;
    private String name;

    public PetMaster toEntity(ImageUploads mainImage) {
        return PetMaster.builder()
                .date(date)
                .mainImage(mainImage)
                .ownerInfo(ownerInfo)
                .petDetailInfo(petDetailInfo)
                .introText(introText)
                .likeWord(likeWord)
                .hateWord(hateWord)
                .name(name)
                .build();
    }

    @Override
    public String toString() {
        return "PetMasterRequestDto{" +
                "ownerInfo=" + (ownerInfo != null ? ownerInfo.toString() : "null") +
                ", petDetailInfo=" + (petDetailInfo != null ? petDetailInfo.toString() : "null") +
                ", letterTxt='" + introText + '\'' +
                ", mainTxt='" + likeWord + '\'' +
                ", greetTxt='" + hateWord + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
