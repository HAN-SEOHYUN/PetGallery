package com.example.wedlessInvite.service;

import com.example.wedlessInvite.domain.Invitation.*;
import com.example.wedlessInvite.dto.InvitationMasterRequestDto;
import com.example.wedlessInvite.dto.InvitationMasterResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InvitationService {

    private final InvitationMasterRepository invitationMasterRepository;

    private final BrideInfoRepository brideInfoRepository;

    private final GroomInfoRepository groomInfoRepository;

    public InvitationMaster saveInvitationMaster(InvitationMasterRequestDto request) {
        BrideInfo brideInfo = brideInfoRepository.save(request.getBrideInfo());
        GroomInfo groomInfo = groomInfoRepository.save(request.getGroomInfo());

        return invitationMasterRepository.save(request.toEntity());

    }

    public Page<InvitationMasterResponseDto> getAllInvitations(Pageable pageable) {
        Page<InvitationMaster> entity = invitationMasterRepository.findAll(pageable);

        // Entity에서 DTO로 변환
        return entity.map(invitation -> InvitationMasterResponseDto.builder()
                .id(invitation.getId())
                .date(invitation.getDate())
                .brideInfo(invitation.getBrideInfo())
                .groomInfo(invitation.getGroomInfo())
                .mainImage(invitation.getMainImage())
                .letterTxt(invitation.getLetterTxt())
                .mainTxt(invitation.getMainTxt())
                .greetTxt(invitation.getGreetTxt())
                .build());
    }

    public InvitationMasterResponseDto getInvitationDetail(Long id) {
        InvitationMaster entity = invitationMasterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invitation not found for ID: " + id));

        return InvitationMasterResponseDto.builder()
                .id(entity.getId())
                .brideInfo(entity.getBrideInfo())
                .groomInfo(entity.getGroomInfo())
                .mainImage(entity.getMainImage())
                .date(entity.getDate())
                .letterTxt(entity.getLetterTxt())
                .mainTxt(entity.getMainTxt())
                .greetTxt(entity.getGreetTxt())
                .build();
    }
}
