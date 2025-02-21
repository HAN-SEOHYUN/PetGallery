package com.example.wedlessInvite.controller.api;

import com.example.wedlessInvite.common.logtrace.LogTrace;
import com.example.wedlessInvite.common.template.AbstractLogTraceTemplate;
import com.example.wedlessInvite.domain.Invitation.InvitationMaster;
import com.example.wedlessInvite.dto.InvitationMasterRequestDto;
import com.example.wedlessInvite.dto.InvitationMasterResponseDto;
import com.example.wedlessInvite.service.InvitationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/invitations")
@RequiredArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;
    private final LogTrace trace;

    @PostMapping
    public ResponseEntity<InvitationMaster> createInvitation(
            @Valid @RequestBody InvitationMasterRequestDto dto) throws IOException {

        AbstractLogTraceTemplate<ResponseEntity<InvitationMaster>> template = new AbstractLogTraceTemplate<>(trace) {
            @Override
            protected ResponseEntity<InvitationMaster> call() throws IOException {
                InvitationMaster savedInvitation = invitationService.saveInvitationMaster(dto);
                return ResponseEntity.ok(savedInvitation);
            }
        };
        return template.execute("OrderController.createInvitation()");
    }

    @GetMapping
    public ResponseEntity<Page<InvitationMasterResponseDto>> getInvitationList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InvitationMasterResponseDto> invitationList = invitationService.getAllInvitations(pageable);
        return ResponseEntity.ok(invitationList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvitationMasterResponseDto> getInvitationDetail(@PathVariable Long id) {
        InvitationMasterResponseDto invitationDetail = invitationService.getInvitationDetail(id);
        return ResponseEntity.ok(invitationDetail);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvitation(@PathVariable Long id) {
        invitationService.deleteInvitation(id);
        return ResponseEntity.noContent().build(); // 204 No Content 반환
    }
}
