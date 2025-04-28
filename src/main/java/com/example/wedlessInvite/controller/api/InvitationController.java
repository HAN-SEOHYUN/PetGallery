package com.example.wedlessInvite.controller.api;

import com.example.wedlessInvite.common.logtrace.LogTrace;
import com.example.wedlessInvite.common.template.AbstractLogTraceTemplate;
import com.example.wedlessInvite.common.template.SuccessResponse;
import com.example.wedlessInvite.domain.Invitation.InvitationMaster;
import com.example.wedlessInvite.domain.User.MasterUser;
import com.example.wedlessInvite.domain.User.MasterUserRepository;
import com.example.wedlessInvite.dto.InvitationMasterRequestDto;
import com.example.wedlessInvite.dto.InvitationMasterResponseDto;
import com.example.wedlessInvite.service.InvitationService;
import com.example.wedlessInvite.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.example.wedlessInvite.common.VarConst.SIGN_UP_SUCCESS_MESSAGE;

@RestController
@RequestMapping("/api/invitations")
@RequiredArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;
    private final MasterUserRepository userRepository;
    private final LogTrace trace;

    @PostMapping
    public ResponseEntity<SuccessResponse<InvitationMaster>> createInvitation(
            @Valid @RequestBody InvitationMasterRequestDto dto) throws IOException {

        AbstractLogTraceTemplate<ResponseEntity<SuccessResponse<InvitationMaster>>> template = new AbstractLogTraceTemplate<>(trace) {
            @Override
            protected ResponseEntity<SuccessResponse<InvitationMaster>> call() throws IOException {


                InvitationMaster savedInvitation = invitationService.saveInvitationMaster(dto);

                SuccessResponse<InvitationMaster> successResponse = new SuccessResponse<>(
                        HttpStatus.CREATED,
                        SIGN_UP_SUCCESS_MESSAGE,
                        savedInvitation
                );
                return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
            }
        };
        return template.execute("InvitationController.createInvitation()");
    }

    @GetMapping
    public ResponseEntity<Page<InvitationMasterResponseDto>> getInvitationList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) throws IOException {

        AbstractLogTraceTemplate<ResponseEntity<Page<InvitationMasterResponseDto>>> template = new AbstractLogTraceTemplate<>(trace) {
            @Override
            protected ResponseEntity<Page<InvitationMasterResponseDto>> call() throws IOException {
                Pageable pageable = PageRequest.of(page, size);
                Page<InvitationMasterResponseDto> invitationList = invitationService.getAllInvitations(pageable);
                return ResponseEntity.ok(invitationList);
            }
        };

        return template.execute("InvitationController.getInvitationList()");
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvitationMasterResponseDto> getInvitationDetail(@PathVariable Long id) throws IOException {

        AbstractLogTraceTemplate<ResponseEntity<InvitationMasterResponseDto>> template = new AbstractLogTraceTemplate<>(trace) {
            @Override
            protected ResponseEntity<InvitationMasterResponseDto> call() throws IOException {
                InvitationMasterResponseDto invitationDetail = invitationService.getInvitationDetail(id);
                return ResponseEntity.ok(invitationDetail);
            }
        };
        return template.execute("InvitationController.getInvitationDetail()");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvitation(@PathVariable Long id) throws IOException {

        AbstractLogTraceTemplate<ResponseEntity<Void>> template = new AbstractLogTraceTemplate<>(trace) {
            @Override
            protected ResponseEntity<Void> call() throws IOException {
                invitationService.deleteInvitation(id);
                return ResponseEntity.noContent().build();
            }
        };

        return template.execute("InvitationController.deleteInvitation()");
    }
}
