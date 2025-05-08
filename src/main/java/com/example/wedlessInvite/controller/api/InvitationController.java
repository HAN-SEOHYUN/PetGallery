package com.example.wedlessInvite.controller.api;

import com.example.wedlessInvite.common.logtrace.LogTrace;
import com.example.wedlessInvite.common.template.AbstractLogTraceTemplate;
import com.example.wedlessInvite.common.template.SuccessResponse;
import com.example.wedlessInvite.domain.User.UserMasterRepository;
import com.example.wedlessInvite.dto.InvitationMasterRequestDto;
import com.example.wedlessInvite.dto.InvitationMasterResponseDto;
import com.example.wedlessInvite.service.InvitationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

import static com.example.wedlessInvite.common.VarConst.*;

@RestController
@RequestMapping("/api/invitations")
@RequiredArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;
    private final UserMasterRepository userMasterRepository;
    private final LogTrace trace;

    @PostMapping
    public ResponseEntity<SuccessResponse<InvitationMasterResponseDto>> createInvitation(
            @Valid @RequestBody InvitationMasterRequestDto dto) throws IOException {

        AbstractLogTraceTemplate<ResponseEntity<SuccessResponse<InvitationMasterResponseDto>>> template = new AbstractLogTraceTemplate<>(trace) {
            @Override
            protected ResponseEntity<SuccessResponse<InvitationMasterResponseDto>> call() throws IOException {

                // InvitationMaster 저장 및 반환된 DTO 변환
                InvitationMasterResponseDto savedInvitationDto = invitationService.saveInvitationMaster(dto);

                // SuccessResponse 생성
                SuccessResponse<InvitationMasterResponseDto> successResponse = new SuccessResponse<>(
                        HttpStatus.CREATED,
                        CREATED_SUCCESS_MESSAGE,
                        savedInvitationDto
                );

                // ResponseEntity로 감싸서 반환
                return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
            }
        };
        return template.execute("InvitationController.createInvitation()");
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<Page<InvitationMasterResponseDto>>> getInvitationList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) throws IOException {
        Pageable pageable = PageRequest.of(page, size);
        Page<InvitationMasterResponseDto> invitationListDto = invitationService.getAllInvitations(pageable);

        // SuccessResponse 생성
        SuccessResponse<Page<InvitationMasterResponseDto>> successResponse = new SuccessResponse<>(
                HttpStatus.OK,
                INVITATION_LIST_FETCH_SUCCESS_MESSAGE,
                invitationListDto
        );
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    @GetMapping("/")
    public ResponseEntity<SuccessResponse<InvitationMasterResponseDto>> getInvitationDetail(@RequestParam String accessKey) throws IOException {

        AbstractLogTraceTemplate<ResponseEntity<SuccessResponse<InvitationMasterResponseDto>>> template = new AbstractLogTraceTemplate<>(trace) {
            @Override
            protected ResponseEntity<SuccessResponse<InvitationMasterResponseDto>> call() throws IOException {
                InvitationMasterResponseDto invitationDetail = invitationService.getInvitationDetail(accessKey);
                SuccessResponse<InvitationMasterResponseDto> response = new SuccessResponse<>(
                        HttpStatus.OK,
                        GET_SUCCESS_MESSAGE,
                        invitationDetail
                );
                return ResponseEntity.ok(response);
            }
        };
        return template.execute("InvitationController.getInvitationDetail()");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<Boolean>> deleteInvitation(@PathVariable Long id) throws IOException {
        AbstractLogTraceTemplate<ResponseEntity<SuccessResponse<Boolean>>> template = new AbstractLogTraceTemplate<>(trace) {
            @Override
            protected ResponseEntity<SuccessResponse<Boolean>> call() throws IOException {
                invitationService.deleteInvitation(id);
                SuccessResponse<Boolean> response = new SuccessResponse<>(
                        HttpStatus.OK,
                        DELETED_SUCCESS_MESSAGE,
                        null
                );
                return ResponseEntity.ok(response);
            }
        };
        return template.execute("InvitationController.deleteInvitation()");
    }
}
