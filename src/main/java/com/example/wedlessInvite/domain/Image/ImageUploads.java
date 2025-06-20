package com.example.wedlessInvite.domain.Image;

import com.example.wedlessInvite.domain.BaseEntity;
import com.example.wedlessInvite.domain.Pet.PetMaster;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="IMAGE_UPLOADS")
public class ImageUploads extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="IU_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IU_PM_ID", nullable = true)
    @Comment("PET_MASTER_ID")
    @JsonIgnore
    private PetMaster petId;

    @Column(name="IU_FILE_NAME", nullable = false, length = 255, unique = true)
    @Comment("S3에 저장된 파일의 이름")
    private String fileName;

    @Column(name = "IU_ORG_FILE_NAME", nullable = false, length = 255)
    @Comment("업로드한 사용자가 제공한 원본 파일 이름")
    private String orgFileName;

    @Column(name="IU_S3_URL", columnDefinition = "TEXT")
    @Comment("S3에 업로드된 파일의 전체 URL")
    private String s3Url;

    @Column(name="IU_FILE_SIZE", columnDefinition = "BIGINT")
    private Long fileSize;

    @Column(name="IU_FILE_TYPE")
    private String fileType;

    @Column(name="IS_DELETED")
    private Boolean isDeleted;

    public void setPetId(PetMaster petMaster) {
        this.petId = petMaster;
    }

    @Builder
    public ImageUploads(PetMaster petId, String fileName, String orgFileName, String s3Url, Long fileSize, String fileType, Boolean isDeleted) {
        this.petId = petId;
        this.fileName = fileName;
        this.orgFileName = orgFileName;
        this.s3Url = s3Url;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.isDeleted = isDeleted;
    }

}
