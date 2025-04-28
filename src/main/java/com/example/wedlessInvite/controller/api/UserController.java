package com.example.wedlessInvite.controller.api;

import com.example.wedlessInvite.domain.Invitation.InvitationMaster;
import com.example.wedlessInvite.domain.User.MasterUser;
import com.example.wedlessInvite.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<MasterUser> register(@RequestParam String name, @RequestParam String password) {
        MasterUser savedUser = userService.register(name, password);
        return ResponseEntity.ok(savedUser);
    }
}
