package com.example.wedlessInvite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class wedlessInviteController {
    @RequestMapping("/")
    public String hello() {
        return "hello world";
    }
}
