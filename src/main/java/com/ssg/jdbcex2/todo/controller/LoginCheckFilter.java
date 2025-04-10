package com.ssg.jdbcex2.todo.controller;


import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// 투두의 하위 페이지들은 필터를 통해서만 들어와~
@WebFilter(urlPatterns = {"/todo/*"}) // 이 경로로 요청되는 모든것들에 대해 필터 실행하겠다
@Log4j2
public class LoginCheckFilter implements Filter { // http servelt기반 아님. 일반 서블릿에서 제공하는 서블릿

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        log.info("Login....doFilter");
        //filterChain.doFilter(servletRequest, servletResponse);

        // 필터에서 받은 req, resp는 인터페이스위 상위 타입임.
        // 실제로 대부분 이 ㅐㄱ체는 httpserveletrequest, HttpServletReponse의 구현체임
        // 필터는 모든 종류의 서블릿 요청을 처리할 수 있도록 범용적인 ServletRequest를 받음. 하지만 우리가 사용하는건 HTTP요청/응답
        HttpServletRequest req = (HttpServletRequest) request; // downcasting to HttpServletRequest
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        if(session.getAttribute("logininfo") == null){
            log.info("로그인 정보가 없는 사용자");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // 세션에 logininfo가 없다면,
    }

    // http 가 아니라서 REQ, RES얻어와야 함.
}
