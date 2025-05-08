package com.example.wedlessInvite.domain.Pet;

import com.example.wedlessInvite.domain.BaseEntity;
import com.example.wedlessInvite.domain.Image.ImageUploads;
import com.example.wedlessInvite.domain.User.UserMaster;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "PET_MASTER")
public class PetMaster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PM_ID")
    private Long id;

    @Column(unique = true, nullable = false, length = 64)
    private String accessKey;

    @ManyToOne
    @JoinColumn(name = "PM_MU_ID", nullable = false)
    @Comment("사용자 정보")
    private UserMaster userMaster;

    @Column(name = "PM_D_DATE", nullable = false)
    @Comment("반려동물 생일")
    private LocalDate date;

    @OneToOne
    @JoinColumn(name = "PM_OI_ID", nullable = false)
    @Comment("반려동물 집사 정보")
    private OwnerInfo ownerInfo;

    @OneToOne
    @JoinColumn(name = "PM_PDI_ID", nullable = false)
    @Comment("반려동물 상세 정보")
    private PetDetailInfo petDetailInfo;

    @OneToOne
    @JoinColumn(name = "PM_IU_ID")
    @Comment("반려동물 메인 이미지")
    private ImageUploads mainImage;

    @OneToMany(mappedBy = "petId", cascade = CascadeType.ALL, orphanRemoval = true)
    @Comment("반려동물 이미지")
    @JsonIgnore
    private List<ImageUploads> imageList;

    @Column(name = "PM_INTRO_TEXT")
    @Comment("반려동물 한줄소개")
    private String introText;

    @Column(name = "PM_LIKE_WORD")
    @Comment("반려동물이 좋아하는 단어")
    private String likeWord;

    @Column(name = "PM_HATE_WORD")
    @Comment("반려동물이 싫어하는 단어")
    private String hateWord;

    @Column(nullable = false)
    private String deleteYN;

    @Builder
    public PetMaster(LocalDate date, OwnerInfo ownerInfo, PetDetailInfo petDetailInfo, ImageUploads mainImage, List<ImageUploads> imageList, String introText, String likeWord, String hateWord, String deleteYN, UserMaster userMaster, String accessKey) {
        this.date = date;
        this.ownerInfo = ownerInfo;
        this.petDetailInfo = petDetailInfo;
        this.mainImage = mainImage;
        this.imageList = imageList;
        this.introText = introText;
        this.likeWord = likeWord;
        this.hateWord = hateWord;
        this.deleteYN = "N";
        this.userMaster = userMaster;
        this.accessKey = UUID.randomUUID().toString();
    }

    public void setDeleted(String deleted) {
        this.deleteYN = deleted;
    }

    public void setUserMaster(UserMaster userMaster) {
        this.userMaster = userMaster;
    }
}
