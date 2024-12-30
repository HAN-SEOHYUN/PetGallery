package com.example.wedlessInvite.service;

import com.example.wedlessInvite.domain.Invitation.*;
import com.example.wedlessInvite.dto.InvitationMasterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InvitationService {

    private final InvitationMasterRepository invitationMasterRepository;

    private final BrideInfoRepository brideInfoRepository;

    private final GroomInfoRepository groomInfoRepository;

    public InvitationMaster saveInvitationMaster(InvitationMasterDto request) {
        BrideInfo brideInfo = brideInfoRepository.save(request.getBrideInfo());
        
        GroomInfo groomInfo = groomInfoRepository.save(request.getGroomInfo());

        return invitationMasterRepository.save(request.toEntity());

    }
}
