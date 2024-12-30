package com.example.wedlessInvite.controller;

import com.example.wedlessInvite.domain.Invitation.InvitationMaster;
import com.example.wedlessInvite.dto.InvitationMasterDto;
import com.example.wedlessInvite.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invitation")
@RequiredArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;

    /**
     * 초대장 생성 API
     *
     * @param request 초대장 생성 요청 DTO
     * @return 생성된 초대장 정보
     */
    @PostMapping
    public ResponseEntity<InvitationMaster> createInvitation(@RequestBody InvitationMasterDto request) {
        InvitationMaster savedInvitation = invitationService.saveInvitationMaster(request);
        return ResponseEntity.ok(savedInvitation);
    }
}
