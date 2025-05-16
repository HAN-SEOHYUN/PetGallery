package com.example.wedlessInvite.controller.api;

import com.example.wedlessInvite.common.template.AbstractResponse;
import com.example.wedlessInvite.common.template.FailureResponse;
import com.example.wedlessInvite.common.template.SuccessResponse;
import com.example.wedlessInvite.domain.User.UserMaster;
import com.example.wedlessInvite.dto.LoginRequestDto;
import com.example.wedlessInvite.dto.RegisterRequestDto;
import com.example.wedlessInvite.dto.UserMasterResponseDto;
import com.example.wedlessInvite.service.UserMasterService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserMasterController {

    private final UserMasterService userMasterService;

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<UserMaster>> register(@RequestBody RegisterRequestDto request) {
        UserMaster savedUser = userMasterService.register(request.getName(), request.getPassword());
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK, "회원가입 성공", savedUser));
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<Long>> login(@RequestBody LoginRequestDto request, HttpSession session) {
        UserMaster user = userMasterService.login(request.getName(), request.getPassword());
        session.setAttribute("userMaster", UserMasterResponseDto.fromEntity(user));

        SuccessResponse<Long> response = new SuccessResponse<>(
                HttpStatus.OK,
                "로그인 성공",
                user.getId()
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<SuccessResponse<?>> me(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        UserMasterResponseDto dto = userMasterService.getAuthenticatedUser(userId);

        SuccessResponse<UserMasterResponseDto> response = new SuccessResponse<>(
                HttpStatus.OK,
                "사용자 정보 조회 성공",
                dto
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<SuccessResponse<Void>> logout(HttpSession session) {
        session.invalidate();  // 세션 무효화
        SuccessResponse<Void> response = new SuccessResponse<>(
                HttpStatus.OK,
                "로그아웃 성공",
                null
        );
        return ResponseEntity.ok(response);
    }
}
