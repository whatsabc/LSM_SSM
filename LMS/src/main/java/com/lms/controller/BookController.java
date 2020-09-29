package com.lms.controller;

import com.lms.service.BookService;
import com.lms.service.serviceimpl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 1. 使用RequestMapping注解来映射请求的URL，写在方法上面，一个请求对应一个方法
 * 2. 返回值会通过视图解析器解析为实际的物理视图, 会做如下解析
 * 通过prefix+returnVal+suffix 这样的方式得到实际的物理视图，然后会转发操作
 * "/WEB-INF/views/success.jsp"
 */
@Controller("book")
@RequestMapping("/book")
public class BookController {

    //@Resource(name="bookServiceImpl")
    @Autowired
    private BookService bookService;

    @RequestMapping(path="/borrowBook")
    public String borrowBook(){
        System.out.println("表示层，查询所有的账户信息");
        bookService.springAPIBookBorrow(100000,"123456789");
        return "success";
    }
}
