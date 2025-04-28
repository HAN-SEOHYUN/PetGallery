package com.example.wedlessInvite.controller.api;

import com.example.wedlessInvite.common.template.SuccessResponse;
import com.example.wedlessInvite.domain.User.MasterUser;
import com.example.wedlessInvite.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<MasterUser>> register(
            @RequestParam String name,
            @RequestParam String password
    ) {
        MasterUser savedUser = userService.register(name, password);
        return ResponseEntity.ok(new SuccessResponse<>(HttpStatus.OK, "회원가입 성공", savedUser));
    }
}
