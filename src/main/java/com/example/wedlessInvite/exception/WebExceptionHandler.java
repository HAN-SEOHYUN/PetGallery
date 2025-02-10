package com.example.wedlessInvite.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.ui.Model;

@ControllerAdvice(basePackages = "com.example.wedlessInvite.web")
public class WebExceptionHandler {
    // 404
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleNotFound(NoHandlerFoundException ex, Model model) {
        model.addAttribute("message", "페이지를 찾을 수 없습니다.");
        return new ModelAndView("error/404", HttpStatus.NOT_FOUND);
    }

    // 500
    @ExceptionHandler(Exception.class)
    public ModelAndView handleServerError(Exception ex, Model model) {
        model.addAttribute("message", "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
        return new ModelAndView("error/500", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
