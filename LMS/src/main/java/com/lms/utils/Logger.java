package com.lms.utils;

import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * 用于记录日志的类，里面提供了公共的代码
 */
@Component("logger")
@Aspect//表示当前类是一个切面类
@EnableAspectJAutoProxy//开启AOP的注解支持
public class Logger {

    @Pointcut("execution(* com.lms.service.serviceimpl.*.*(..))")//切入点表达式
    private void pt1(){}

    /**
     * 用于打印日志：计划让其在切入点方法执行之前执行
     */
    @Before("pt1()")
    public void beforePrintLog(){
        System.out.println("前置通知...");
    }

    @AfterReturning("pt1()")
    public void afterReturningPrintLog(){
        System.out.println("后置通知...");
    }

    @AfterThrowing("pt1()")
    public void afterThrowingPrintLog(){
        System.out.println("异常通知...");
    }

    @After("pt1()")
    public void afterPrintLog(){
        System.out.println("最终通知...");
    }
}
