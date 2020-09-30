package com.lms.controller;

import com.lms.bean.User;
import com.lms.dao.daoimpl.UserDaoImpl;
import com.lms.service.BookService;
import com.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller("user")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 进行登录验证，返回状态码-1，0，1，分别表示无此账户，密码错误，登陆成功
     * @param httpServletRequest
     * @param httpServletResponse
     * @throws Exception
     */
    @RequestMapping(path="/login")
    public String loginVerify(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception{
        String userId = httpServletRequest.getParameter("userId");
        String password = httpServletRequest.getParameter("password");
        String rememberMe = httpServletRequest.getParameter("rememberMe");

        int returnCode=userService.loginVerify(userId,password);
        if(returnCode==1){
            //添加session
            httpServletRequest.getSession().setAttribute("user", userId);
            //添加cookie
            if(rememberMe.equals("true")) {
                //创建两个Cookie对象
                Cookie nameCookie=new Cookie("userId", userId);
                //设置Cookie的有效期为3天
                nameCookie.setMaxAge(60*60*24*3);
                Cookie pwdCookie=new Cookie("password", password);
                pwdCookie.setMaxAge(60*60*24*3);
                httpServletResponse.addCookie(nameCookie);
                httpServletResponse.addCookie(pwdCookie);
            }
            return "success";
        }
        httpServletResponse.getWriter().write(returnCode);
        return null;
    }
}