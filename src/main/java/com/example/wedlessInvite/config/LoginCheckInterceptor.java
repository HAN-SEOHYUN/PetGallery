package com.example.wedlessInvite.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

public class LoginCheckInterceptor implements HandlerInterceptor {

    // 로그인 체크 제외할 URI 패턴 리스트 (필요 시 추가 가능)
    private static final List<String> EXCLUDE_URIS = List.of(
            "/api/likes/rank"
    );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        if (isExcludedUri(uri)) {
            return true;
        }

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userMaster") == null) {
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }

    /**
     * 로그인 체크 제외 대상 URI인지 판단
     * - 완전 일치하는 경우 또는 특정 패턴으로 시작하는 경우도 가능
     */
    private boolean isExcludedUri(String uri) {
        // 완전 일치 검사
        if (EXCLUDE_URIS.contains(uri)) {
            return true;
        }

        // 특정 경로로 시작하는 경우 제외 (예: /public/**)
        for (String exclude : EXCLUDE_URIS) {
            if (exclude.endsWith("/**")) {
                String base = exclude.substring(0, exclude.length() - 3);
                if (uri.startsWith(base)) {
                    return true;
                }
            }
        }

        return false;
    }
}
