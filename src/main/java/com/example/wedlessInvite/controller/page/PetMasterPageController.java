package com.example.wedlessInvite.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PetMasterPageController {

    @GetMapping("/create")
    public String showCreatePage() {
        return "pages/create";
    }

    @GetMapping("/main")
    public String showMainPage() {
        return "pages/main";
    }

    @GetMapping("/list")
    public String showListPage() {
        return "pages/list";
    }

    @GetMapping("/detail")
    public String showDetailPage() {
        return "pages/detail";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "pages/register";
    }
}
