package com.example.wedlessInvite.service;

import com.example.wedlessInvite.domain.Invitation.*;
import com.example.wedlessInvite.dto.InvitationMasterRequestDto;
import com.example.wedlessInvite.dto.InvitationMasterResponseDto;
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

    public List<InvitationMasterResponseDto> getAllInvitations() {
        List<InvitationMaster> entity =  invitationMasterRepository.findAll();
        List<InvitationMasterResponseDto> dto = new ArrayList<>();

        for (InvitationMaster invitation : entity) {
            InvitationMasterResponseDto responseDto = InvitationMasterResponseDto.builder()
                    .id(invitation.getId())
                    .date(invitation.getDate())
                    .brideInfo(invitation.getBrideInfo())
                    .groomInfo(invitation.getGroomInfo())
                    .letterTxt(invitation.getLetterTxt())
                    .mainTxt(invitation.getMainTxt())
                    .greetTxt(invitation.getGreetTxt())
                    .build();
            dto.add(responseDto);
        }
        return dto;
    }


}
