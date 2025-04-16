package com.example.wedlessInvite.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PortfolioPageController {
    @GetMapping("/portfolio")
    public String showCreatePage() {
        return "portfolio/main";
    }
}
