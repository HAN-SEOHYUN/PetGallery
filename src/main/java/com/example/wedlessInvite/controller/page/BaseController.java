package com.example.wedlessInvite.controller.page;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class BaseController {

    @ModelAttribute
    public void addCommonAttributes(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        model.addAttribute("isLoggedIn", userId != null);  // 로그인 상태 여부 추가
    }
}
