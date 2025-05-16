package com.example.wedlessInvite.controller.api;

import com.example.wedlessInvite.common.template.SuccessResponse;
import com.example.wedlessInvite.dto.PetLikeRankingResponseDto;
import com.example.wedlessInvite.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.wedlessInvite.common.VarConst.*;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/{petId}")
    public ResponseEntity<SuccessResponse<Boolean>> toggleLike(@PathVariable Long petId,
                                                            @RequestParam Long userId) {
        boolean isLiked = likeService.toggleLike(petId, userId);

        String message = isLiked ? "좋아요 등록되었습니다." : "좋아요가 취소되었습니다.";
        SuccessResponse<Boolean> successResponse = new SuccessResponse<>(
                HttpStatus.OK,
                message,
                isLiked
        );
        return ResponseEntity.ok(successResponse);
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

    @GetMapping
    public ResponseEntity<SuccessResponse<List<PetLikeRankingResponseDto>>> getPetLike() {
        List<PetLikeRankingResponseDto> dto = likeService.getTop5MostLikedInvitations();
        SuccessResponse<List<PetLikeRankingResponseDto>> successResponse = new SuccessResponse<>(
                HttpStatus.OK,
                LIKE_LIST_FETCH_SUCCESS_MESSAGE,
                dto
        );
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
}
