package com.example.wedlessInvite.dto;

import com.example.wedlessInvite.domain.Image.ImageUploads;
import com.example.wedlessInvite.domain.Invitation.BrideInfo;
import com.example.wedlessInvite.domain.Invitation.GroomInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class InvitationMasterResponseDto {
    private Long id;
    private LocalDate date;
    private BrideInfo brideInfo;
    private GroomInfo groomInfo;
    private ImageUploads mainImage;
    private List<ImageUploadDto> imageList;
    private String letterTxt;
    private String mainTxt;
    private String greetTxt;

    @Builder
    public InvitationMasterResponseDto(Long id, LocalDate date, BrideInfo brideInfo, GroomInfo groomInfo,ImageUploads mainImage, List<ImageUploadDto> imageList, String letterTxt, String mainTxt, String greetTxt) {
        this.id = id;
        this.date = date;
        this.brideInfo = brideInfo;
        this.groomInfo = groomInfo;
        this.mainImage = mainImage;
        this.imageList = imageList;
        this.letterTxt = letterTxt;
        this.mainTxt = mainTxt;
        this.greetTxt = greetTxt;
    }
}
