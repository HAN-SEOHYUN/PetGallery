package com.example.wedlessInvite.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class wedlessInviteController {
    @RequestMapping("/")
    public String hello() {
        return "hello world";
    }
}
