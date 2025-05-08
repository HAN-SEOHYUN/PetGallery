package com.example.wedlessInvite.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/invitations")
public class PetMasterPageController {

    @GetMapping("/create")
    public String showCreatePage() {
        return "invitations/create";
    }

    @GetMapping("/main")
    public String showMainPage() {
        return "invitations/main";
    }

    @GetMapping("/list")
    public String showListPage() {
        return "invitations/list";
    }

    @GetMapping("/detail")
    public String showDetailPage() {
        return "invitations/detail";
    }
}
