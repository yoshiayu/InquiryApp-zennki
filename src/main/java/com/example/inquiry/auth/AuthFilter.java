package com.example.inquiry.auth;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * /jsp/* をログイン必須にするシンプルなフィルタ。
 * 例外パスは /jsp/login.jsp だけ（ログイン画面）。
 */
@WebFilter(urlPatterns = {"/jsp/*"})
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  req  = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());

        // ログイン画面は通す
        if (path.equals("/jsp/login.jsp")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("user") != null);

        if (loggedIn) {
            chain.doFilter(request, response);
        } else {
            // 戻り先を保存してログインへ
            String full = req.getRequestURL().toString();
            if (req.getQueryString() != null) full += "?" + req.getQueryString();
            req.getSession(true).setAttribute("BACK_URL", full);
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
