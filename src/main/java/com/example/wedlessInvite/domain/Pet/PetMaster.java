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
    @Comment("결혼일자")
    private LocalDate date;

    @OneToOne
    @JoinColumn(name = "PM_OI_ID", nullable = false)
    @Comment("신부 정보")
    private OwnerInfo ownerInfo;

    @OneToOne
    @JoinColumn(name = "PM_GI_ID", nullable = false)
    @Comment("신랑 정보")
    private GroomInfo groomInfo;

    @OneToOne
    @JoinColumn(name = "PM_IU_ID")
    @Comment("메인 이미지")
    private ImageUploads mainImage;

    @OneToMany(mappedBy = "petId", cascade = CascadeType.ALL, orphanRemoval = true)
    @Comment("웨딩사진")
    @JsonIgnore
    private List<ImageUploads> imageList;

    @Column(name = "PM_LETTER_TEXT")
    @Comment("레터링 문구")
    private String letterTxt;

    @Column(name = "PM_MAIN_TEXT")
    @Comment("메인 텍스트")
    private String mainTxt;

    @Column(name = "PM_GREETING_TEXT")
    @Comment("인사말")
    private String greetTxt;

    @Column(nullable = false)
    private String deleteYN;

    @Builder
    public PetMaster(LocalDate date, OwnerInfo ownerInfo, GroomInfo groomInfo, ImageUploads mainImage, List<ImageUploads> imageList, String letterTxt, String mainTxt, String greetTxt, String deleteYN, UserMaster userMaster, String accessKey) {
        this.date = date;
        this.ownerInfo = ownerInfo;
        this.groomInfo = groomInfo;
        this.mainImage = mainImage;
        this.imageList = imageList;
        this.letterTxt = letterTxt;
        this.mainTxt = mainTxt;
        this.greetTxt = greetTxt;
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
