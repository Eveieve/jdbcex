package com.ssg.jdbcex2.todo.controller;

import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
@Log4j2
public class LogOutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // doGet호출되지 않을거니까 놔둬도 상관 없음.
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doPost...");
        // 현재 세션 받아오기
        HttpSession session = req.getSession();
        session.removeAttribute("loginfo"); // delete session
        session.invalidate();
        resp.sendRedirect("/"); // redirect to root page

    }
}
