package org.lanqiao.thesismanager.interceptors;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Auther: WDS
 * @Date: 2019/1/11 15:07
 * @Description:
 */
public class ManagerLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(!StringUtils.isEmpty(session.getAttribute("user"))){
            return true;
        }
        request.setAttribute("msg","权限不足，请登录！");
        request.getRequestDispatcher("/manager/login").forward(request,response);
        return false;
    }
}
