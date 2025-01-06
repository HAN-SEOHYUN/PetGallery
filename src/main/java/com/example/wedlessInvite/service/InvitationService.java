package com.example.wedlessInvite.service;

import com.example.wedlessInvite.domain.Invitation.*;
import com.example.wedlessInvite.dto.InvitationMasterRequestDto;
import com.example.wedlessInvite.dto.InvitationMasterResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        Page<InvitationMaster> entityPage = invitationMasterRepository.findAll(pageable);

        // Entity에서 DTO로 변환
        return entityPage.map(invitation -> InvitationMasterResponseDto.builder()
                .id(invitation.getId())
                .date(invitation.getDate())
                .brideInfo(invitation.getBrideInfo())
                .groomInfo(invitation.getGroomInfo())
                .letterTxt(invitation.getLetterTxt())
                .mainTxt(invitation.getMainTxt())
                .greetTxt(invitation.getGreetTxt())
                .build());
    }
}
