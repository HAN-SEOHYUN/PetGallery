package com.example.wedlessInvite.controller.api;

import com.example.wedlessInvite.common.logtrace.LogTrace;
import com.example.wedlessInvite.common.template.AbstractLogTraceTemplate;
import com.example.wedlessInvite.common.template.SuccessResponse;
import com.example.wedlessInvite.domain.User.UserMasterRepository;
import com.example.wedlessInvite.dto.PetMasterRequestDto;
import com.example.wedlessInvite.dto.PetMasterResponseDto;
import com.example.wedlessInvite.service.PetMasterService;
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
@RequestMapping("/api")
@RequiredArgsConstructor
public class PetMasterController {
    private final PetMasterService petMasterService;
    private final UserMasterRepository userMasterRepository;
    private final LogTrace trace;

    @PostMapping
    public ResponseEntity<SuccessResponse<PetMasterResponseDto>> createInvitation(
            @Valid @RequestBody PetMasterRequestDto request) throws IOException {

        AbstractLogTraceTemplate<ResponseEntity<SuccessResponse<PetMasterResponseDto>>> template = new AbstractLogTraceTemplate<>(trace) {
            @Override
            protected ResponseEntity<SuccessResponse<PetMasterResponseDto>> call() throws IOException {

                // PetMaster 저장 및 반환된 DTO 변환
                PetMasterResponseDto savedInvitationDto = petMasterService.saveInvitationMaster(request);

                // SuccessResponse 생성
                SuccessResponse<PetMasterResponseDto> successResponse = new SuccessResponse<>(
                        HttpStatus.CREATED,
                        CREATED_SUCCESS_MESSAGE,
                        savedInvitationDto
                );

                // ResponseEntity로 감싸서 반환
                return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
            }
        };
        return template.execute("PetMasterController.createInvitation()");
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<Page<PetMasterResponseDto>>> getInvitationList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) throws IOException {
        Pageable pageable = PageRequest.of(page, size);
        Page<PetMasterResponseDto> invitationListDto = petMasterService.getAllInvitations(pageable);

        // SuccessResponse 생성
        SuccessResponse<Page<PetMasterResponseDto>> successResponse = new SuccessResponse<>(
                HttpStatus.OK,
                INVITATION_LIST_FETCH_SUCCESS_MESSAGE,
                invitationListDto
        );
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    @GetMapping("/")
    public ResponseEntity<SuccessResponse<PetMasterResponseDto>> getInvitationDetail(@RequestParam String accessKey) throws IOException {

        AbstractLogTraceTemplate<ResponseEntity<SuccessResponse<PetMasterResponseDto>>> template = new AbstractLogTraceTemplate<>(trace) {
            @Override
            protected ResponseEntity<SuccessResponse<PetMasterResponseDto>> call() throws IOException {
                PetMasterResponseDto invitationDetail = petMasterService.getInvitationDetail(accessKey);
                SuccessResponse<PetMasterResponseDto> response = new SuccessResponse<>(
                        HttpStatus.OK,
                        GET_SUCCESS_MESSAGE,
                        invitationDetail
                );
                return ResponseEntity.ok(response);
            }
        };
        return template.execute("PetMasterController.getInvitationDetail()");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<Boolean>> deleteInvitation(@PathVariable Long id) throws IOException {
        AbstractLogTraceTemplate<ResponseEntity<SuccessResponse<Boolean>>> template = new AbstractLogTraceTemplate<>(trace) {
            @Override
            protected ResponseEntity<SuccessResponse<Boolean>> call() throws IOException {
                petMasterService.deleteInvitation(id);
                SuccessResponse<Boolean> response = new SuccessResponse<>(
                        HttpStatus.OK,
                        DELETED_SUCCESS_MESSAGE,
                        null
                );
                return ResponseEntity.ok(response);
            }
        };
        return template.execute("PetMasterController.deleteInvitation()");
    }
}
