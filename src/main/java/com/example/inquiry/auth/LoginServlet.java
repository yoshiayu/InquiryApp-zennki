package com.example.inquiry.auth;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // 超入門用の固定ユーザー（あとでDAOへ差し替えOK）
    private static final String DEMO_EMAIL = "user@example.com";
    private static final String DEMO_PASS  = "pass1234";
    private static final String DEMO_NAME  = "体験ユーザー";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // すでにログイン済みならトップへ
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }
        // エラー表示などのためにそのままJSPへ
        req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        String pass  = req.getParameter("password");

        if (DEMO_EMAIL.equals(email) && DEMO_PASS.equals(pass)) {
            HttpSession session = req.getSession(true);
            // “user” が在る＝ログイン済み、という超シンプル運用
            session.setAttribute("user", DEMO_NAME);
            session.setAttribute("userEmail", DEMO_EMAIL);

            // 元の要求URLへ戻す（フィルタが保存してくれてる場合）
            String back = (String) session.getAttribute("BACK_URL");
            if (back != null) {
                session.removeAttribute("BACK_URL");
                resp.sendRedirect(back);
            } else {
                resp.sendRedirect(req.getContextPath() + "/index.jsp");
            }
        } else {
            req.setAttribute("error", "メールアドレスまたはパスワードが違います。");
            req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
        }
    }
}
