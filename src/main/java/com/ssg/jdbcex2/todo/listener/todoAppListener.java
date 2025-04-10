package com.ssg.jdbcex2.todo.listener;

import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener// 이 클래스를 리스너로 등록하겠음
@Log4j2
public class todoAppListener implements ServletContextListener { // ServerletContext 니까 어플리케이션을 리스닝하는 것임


    @Override // 어플리케이션이 동작되기전에 어떤 작업들을 처리하고 싶을때
    public void contextInitialized(ServletContextEvent sce) { // generic servelet에서 본것같은 이름!
        log.info("contextInitialized");

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("appName", "todoService");
    }

    @Override // 어플리케이션이 끝나기 전에 정리해야 할것들 처리하고 싶을때
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("contextDestroyed");
        log.info("contextDestroyed");
        log.info("contextDestroyed");
    }
}
