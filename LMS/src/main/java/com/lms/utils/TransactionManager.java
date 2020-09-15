package com.lms.utils;

import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * 手动实现事务控制器
 * #### 去掉所有AOP注解，在BeanFactory中手动也实现了AOP的原理达到了相同的效果
 * #### Spring自己就有事务管理器，最后的测试中我们使用了Spring自带的事务管理器
 * 事务管理相关的工具类，包含了开启事务，开启事务，提交事务，回滚事务和释放连接
 * @author Jianshu
 * @time 20200914
 */

/**
 * 后续要调试SpringAPI提供的事务控制，把手动实现的先注释掉
@Repository(value="transactionManager")
@Aspect
@EnableAspectJAutoProxy
*/
public class TransactionManager {

    @Resource(name="connectionUtils")
    private ConnectionUtils connectionUtils;

    /**
     * 后续要调试SpringAPI提供的事务控制，把手动实现的先注释掉
     */
    @Pointcut("execution(* com.lms.service.serviceimpl.*.*(..))")
    private void pt1(){}


    /**
     * 开始事务
     */
    @Before("pt1()")
    public void beginTransaction(){
        try {
            connectionUtils.getThreadConnection().setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 提交事务
     */
    @AfterReturning("pt1()")
    public void commit(){
        try {
            connectionUtils.getThreadConnection().commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 回滚事务
     */
    @AfterThrowing("pt1()")
    public void rollback(){
        try {
            connectionUtils.getThreadConnection().rollback();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 释放资源
     */
    @After("pt1()")
    public void release(){
        try {
            connectionUtils.getThreadConnection().close();//还回连接池
            connectionUtils.removeConnection();//进行解绑
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
