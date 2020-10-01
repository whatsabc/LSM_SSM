package com.lms.controller.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserInterceptor implements HandlerInterceptor {

    /**
     * 预处理，controller方法执行前
     * @param request
     * @param response
     * @param handler
     * @return true,放行，执行下一个拦截器，如果没有，执行controller中的方法
     * false 不放行
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession httpSession=request.getSession(false);
        if(httpSession!=null){
            if(httpSession.getAttribute("userId")!=null){
                //放行
                return true;
            }
            //为空，重定向
            else
                response.sendRedirect("login.html");
        }
        else
            response.sendRedirect("login.html");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
