package com.example.wedlessInvite.controller.api;

import com.example.wedlessInvite.common.template.SuccessResponse;
import com.example.wedlessInvite.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.wedlessInvite.common.VarConst.*;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/{petId}")
    public ResponseEntity<SuccessResponse<Void>> like(@PathVariable Long petId, Long userId) {
        likeService.like(petId, userId);

        SuccessResponse<Void> successResponse = new SuccessResponse<>(
                HttpStatus.OK,
                LIKE_SUCCESS_MESSAGE,
                null
        );
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<SuccessResponse<Void>> unlike(@PathVariable Long petId, Long userId) {
        likeService.unlike(petId, userId);

        SuccessResponse<Void> successResponse = new SuccessResponse<>(
                HttpStatus.OK,
                LIKE_CANCEL_MESSAGE,
                null
        );

        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
}
