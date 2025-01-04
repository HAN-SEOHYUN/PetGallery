package com.example.wedlessInvite.controller.api;

import com.example.wedlessInvite.domain.Invitation.InvitationMaster;
import com.example.wedlessInvite.dto.InvitationMasterRequestDto;
import com.example.wedlessInvite.dto.InvitationMasterResponseDto;
import com.example.wedlessInvite.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/invitations")
@RequiredArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;

    @PostMapping
    public ResponseEntity<InvitationMaster> createInvitation(@Valid @RequestBody InvitationMasterRequestDto request) {
        InvitationMaster savedInvitation = invitationService.saveInvitationMaster(request);
        return ResponseEntity.ok(savedInvitation);
    }

    @GetMapping
    public ResponseEntity<List<InvitationMasterResponseDto>> getInvitationList() {
        List<InvitationMasterResponseDto> invitationList = invitationService.getAllInvitations();
        return ResponseEntity.ok(invitationList);
    }
}
