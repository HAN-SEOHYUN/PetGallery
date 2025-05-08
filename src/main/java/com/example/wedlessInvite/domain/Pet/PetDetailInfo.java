package com.example.wedlessInvite.domain.Pet;

import com.example.wedlessInvite.common.YN;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Table(name="PET_DETAIL_INFO")
public class PetDetailInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PDI_ID")
    private Long id;

    @Column(name="PDI_NAME")
    private String name;

    @Column(name="PDI_BIRTH")
    private LocalDate birth;

    @Column(name="PDI_PHONE")
    private String phone;

    @Column(name="PDI_FATHER_NAME")
    private String fatherName;

    @Column(name="PDI_FATHER_PHONE")
    private String fatherPhone;

    @Enumerated(EnumType.STRING)
    @Column(name = "PDI_FATHER_DECEASED_YN", nullable = false, length = 1)
    @Comment("부친 사망 여부 (Y/N)")
    private YN fatherDeceasedYN;

    @Column(name="PDI_MOTHER_NAME")
    private String motherName;

    @Column(name="PDI_MOTHER_PHONE")
    private String motherPhone;

    @Enumerated(EnumType.STRING)
    @Column(name = "PDI_MOTHER_DECEASED_YN", nullable = false, length = 1)
    @Comment("모친 사망 여부 (Y/N)")
    private YN motherDeceasedYN;

    @Builder
    public PetDetailInfo(String name, LocalDate birth, String phone, String fatherName, String fatherPhone, YN fatherDeceasedYN, String motherName, String motherPhone, YN motherDeceasedYN) {
        this.name = name;
        this.birth = birth;
        this.phone = phone;
        this.fatherName = fatherName;
        this.fatherPhone = fatherPhone;
        this.fatherDeceasedYN = fatherDeceasedYN != null ? fatherDeceasedYN : YN.N;
        this.motherName = motherName;
        this.motherPhone = motherPhone;
        this.motherDeceasedYN = motherDeceasedYN != null ? motherDeceasedYN : YN.N;
    }

    @PrePersist
    private void setDefaultValues() {
        if (fatherDeceasedYN == null) {
            fatherDeceasedYN = YN.N;
        }
        if (motherDeceasedYN == null) {
            motherDeceasedYN = YN.N;
        }
    }
}
