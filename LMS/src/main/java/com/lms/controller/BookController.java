package com.lms.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller("sayHello")
public class BookController {

    @RequestMapping(path="/hello")
    public String sayHello(){
        //ApplicationContext applicationContext=new ClassPathXmlApplicationContext("bean.xml");
        System.out.println("hello world");
        return "success";
    }
}
