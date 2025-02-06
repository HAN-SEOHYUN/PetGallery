package com.example.wedlessInvite.domain.Invitation;

import com.example.wedlessInvite.domain.BaseEntity;
import com.example.wedlessInvite.domain.Image.ImageUploads;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "INVITATION_MASTER")
public class InvitationMaster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IM_ID")
    private Long id;

    @Column(name = "IM_D_DATE", nullable = false)
    @Comment("결혼일자")
    private LocalDate date;

    @OneToOne
    @JoinColumn(name = "IM_BI_ID", nullable = false)
    @Comment("신부 정보")
    private BrideInfo brideInfo;

    @OneToOne
    @JoinColumn(name = "IM_GI_ID", nullable = false)
    @Comment("신랑 정보")
    private GroomInfo groomInfo;

    @OneToOne
    @JoinColumn(name = "IM_IU_ID")
    @Comment("메인 이미지")
    private ImageUploads mainImage;

    @OneToMany(mappedBy = "invitationId", cascade = CascadeType.ALL, orphanRemoval = true)
    @Comment("웨딩사진")
    private List<ImageUploads> imageList;

    @Column(name = "IM_LETTER_TEXT")
    @Comment("레터링 문구")
    private String letterTxt;

    @Column(name = "IM_MAIN_TEXT")
    @Comment("메인 텍스트")
    private String mainTxt;

    @Column(name = "IM_GREETING_TEXT")
    @Comment("인사말")
    private String greetTxt;

    @Column(nullable = false)
    private String deleteYN;

    @Builder
    public InvitationMaster(LocalDate date, BrideInfo brideInfo, GroomInfo groomInfo, ImageUploads mainImage, List<ImageUploads> imageList, String letterTxt, String mainTxt, String greetTxt, String deleteYN) {
        this.date = date;
        this.brideInfo = brideInfo;
        this.groomInfo = groomInfo;
        this.mainImage = mainImage;
        this.imageList = imageList;
        this.letterTxt = letterTxt;
        this.mainTxt = mainTxt;
        this.greetTxt = greetTxt;
        this.deleteYN = "N";
    }

    public void setDeleted(String deleted) {
        this.deleteYN = deleted;
    }
}
