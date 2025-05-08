package com.example.wedlessInvite.domain.Invitation;

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
@Table(name="OWNER_INFO")
public class OwnerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="OI_ID")
    private Long id;

    @Column(name="OI_NAME")
    private String name;

    @Column(name="OI_BIRTH")
    private LocalDate birth;

    @Column(name="OI_PHONE")
    private String phone;

    @Column(name="OI_FATHER_NAME")
    private String fatherName;

    @Column(name="OI_FATHER_PHONE")
    private String fatherPhone;

    @Enumerated(EnumType.STRING)
    @Column(name = "OI_FATHER_DECEASED_YN", nullable = false, length = 1)
    @Comment("부친 사망 여부 (Y/N)")
    private YN fatherDeceasedYN;

    @Column(name="OI_MOTHER_NAME")
    private String motherName;

    @Column(name="OI_MOTHER_PHONE")
    private String motherPhone;

    @Enumerated(EnumType.STRING)
    @Column(name = "OI_MOTHER_DECEASED_YN", nullable = false, length = 1)
    @Comment("모친 사망 여부 (Y/N)")
    private YN motherDeceasedYN;

    @Builder
    public OwnerInfo(String name, LocalDate birth, String phone, String fatherName, String fatherPhone, YN fatherDeceasedYN, String motherName, String motherPhone, YN motherDeceasedYN) {
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
