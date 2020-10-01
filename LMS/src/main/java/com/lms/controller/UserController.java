package com.lms.controller;

import com.lms.bean.User;
import com.lms.dao.daoimpl.UserDaoImpl;
import com.lms.service.BookService;
import com.lms.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller("user")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @param httpServletRequest
     * @param httpServletResponse
     * @throws Exception
     */
    @RequestMapping(path="/login")
    @ResponseBody
    public String loginVerify(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception{

        Map<String,String> map=new HashMap<>();
        //获取前端传入信息
        httpServletRequest.getParameterMap();
        String userId = httpServletRequest.getParameter("userId");
        String password = httpServletRequest.getParameter("password");
        String rememberMe = httpServletRequest.getParameter("rememberMe");
        //使用request对象的getSession()获取session，如果session不存在则创建一个
        HttpSession session=httpServletRequest.getSession();
        //验证信息
        String returnCode=userService.loginVerify(userId,password);
        map.put("status",returnCode);
        System.out.println(returnCode);

        //结果匹配
        if(returnCode.equals("true")){
            //添加session
            httpServletRequest.getSession().setAttribute("userId", userId);
            //记住我，添加cookie
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
            System.out.println(new JSONObject(map).toString());
            return new JSONObject(map).toString();
        }
        /**
         * 如果这里要返回的是JavaBean对象，直接返回对象即可，不用转换成Stringp
         */
        System.out.println(new JSONObject(map).toString());
        return new JSONObject(map).toString();
    }

    /**
     * 跳转到主页
     * @return
     */
    @RequestMapping(path="/index")
    public String toIndex(){
        return "redirect:/index.html";
    }
}