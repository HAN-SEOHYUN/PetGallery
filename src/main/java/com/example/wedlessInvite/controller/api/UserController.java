package com.example.wedlessInvite.controller.api;

import com.example.wedlessInvite.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public String register(@RequestParam String name, @RequestParam String password) {
        userService.register(name, password);
        return "회원가입 성공";
    }
}
