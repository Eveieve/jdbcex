package com.ssg.jdbcex2.todo.filter;



import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

// 서블릿은 서버에 등록하는것임
// 투두 등록할때 한글 깨지지 않도록

// 모든 요청에 필터가 적용됨.
@WebFilter(urlPatterns = {"/*"}) // 어떤 요층이든 한글로 변환해서 컨트롤러에 전달하겠음
@Log4j2
public class UTF8Filter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        log.info("UTF8Filter-------------");
        HttpServletRequest req = (HttpServletRequest) request;
        req.setCharacterEncoding("UTF-8");
        filterChain.doFilter(request, response); // 경로로 이동해
    }
}
