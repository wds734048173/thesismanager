package org.lanqiao.thesismanager.interceptors;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//前端拦截器
public class StudentLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(!StringUtils.isEmpty(session.getAttribute("user"))){
            return true;
        }
        request.setAttribute("msg","权限不足，请登录！");
        request.getRequestDispatcher("/student/login").forward(request,response);
        return false;
    }
}
