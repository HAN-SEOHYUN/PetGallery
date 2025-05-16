package com.example.wedlessInvite.controller.page;

import com.example.wedlessInvite.dto.UserMasterResponseDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("userMaster")
    public UserMasterResponseDto loginUser(HttpSession session) {
        return (UserMasterResponseDto) session.getAttribute("userMaster");
    }
}
