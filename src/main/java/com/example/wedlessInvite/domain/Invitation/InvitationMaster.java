package com.example.wedlessInvite.domain.Invitation;

import com.example.wedlessInvite.domain.BaseEntity;
import com.example.wedlessInvite.domain.Post.ImageUploads;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Table(name="INVITATION_MASTER")
public class InvitationMaster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="IM_ID")
    private Long id;

    @Column(name="IM_D_DATE", nullable = false)
    @Comment("결혼일자")
    private LocalDate date;

    @OneToOne
    @JoinColumn(name = "IM_BI_ID", nullable = false)
    @Comment("신부 정보")
    private BrideInfo brideInfoId;

    @OneToOne
    @JoinColumn(name = "IM_GI_ID", nullable = false)
    @Comment("신랑 정보")
    private GroomInfo groomInfoId;

    @OneToOne
    @JoinColumn(name = "IM_IU_ID")
    @Comment("메인 이미지")
    private ImageUploads mainImageId;

    @OneToMany(mappedBy = "invitationId", cascade = CascadeType.ALL, orphanRemoval = true)
    @Comment("웨딩사진")
    private List<ImageUploads> imageList;

    @Column(name="IM_LETTER_TEXT")
    @Comment("레터링 문구")
    private String letterTxt;

    @Column(name="IM_MAIN_TEXT")
    @Comment("메인 텍스트")
    private String mainTxt;

    @Column(name="IM_GREETING_TEXT")
    @Comment("인사말")
    private String greetTxt;
}
