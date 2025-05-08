package com.example.wedlessInvite.dto;

import com.example.wedlessInvite.domain.Pet.PetMaster;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PetLikeRankingResponseDto {
    private String accessKey;
    private LocalDate date; // 반려동물 생일
    private String name; // 반려동물 이름

    @Builder
    public PetLikeRankingResponseDto(PetMaster petMaster) {
        this.accessKey = petMaster.getAccessKey();
        this.date = petMaster.getDate();
        this.name = petMaster.getName();
    }
}


