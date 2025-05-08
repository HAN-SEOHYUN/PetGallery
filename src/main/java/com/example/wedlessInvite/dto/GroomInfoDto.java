package com.example.wedlessInvite.dto;

import com.example.wedlessInvite.common.YN;
import com.example.wedlessInvite.domain.Pet.PetDetailInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GroomInfoDto {
    private String name;
    private LocalDate birth;
    private String phone;
    private String fatherName;
    private String fatherPhone;
    private YN fatherDeceasedYN;
    private String motherName;
    private String motherPhone;
    private YN motherDeceasedYN;

    public PetDetailInfo toEntity() {
        return PetDetailInfo.builder()
                .name(name)
                .birth(birth)
                .phone(phone)
                .fatherName(fatherName)
                .fatherPhone(fatherPhone)
                .fatherDeceasedYN(fatherDeceasedYN)
                .motherName(motherName)
                .motherPhone(motherPhone)
                .motherDeceasedYN(motherDeceasedYN)
                .build();
    }

    @Builder
    public GroomInfoDto(String name, LocalDate birth, String phone, String fatherName, String fatherPhone, YN fatherDeceasedYN, String motherName, String motherPhone, YN motherDeceasedYN) {
        this.name = name;
        this.birth = birth;
        this.phone = phone;
        this.fatherName = fatherName;
        this.fatherPhone = fatherPhone;
        this.fatherDeceasedYN = fatherDeceasedYN;
        this.motherName = motherName;
        this.motherPhone = motherPhone;
        this.motherDeceasedYN = motherDeceasedYN;
    }
}
