package com.example.wedlessInvite.controller.api;

import com.example.wedlessInvite.common.template.SuccessResponse;
import com.example.wedlessInvite.domain.User.UserMaster;
import com.example.wedlessInvite.dto.RegisterRequestDto;
import com.example.wedlessInvite.service.UserMasterService;
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
}
