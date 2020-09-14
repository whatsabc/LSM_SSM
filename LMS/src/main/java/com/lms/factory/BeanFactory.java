package com.lms.factory;

import com.lms.bean.Book;
import com.lms.bean.Record;
import com.lms.bean.User;
import com.lms.service.BookService;
import com.lms.utils.TransactionManager;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Calendar;
import java.util.Date;

/**
 * 我们在配置工厂的时候使用xml方便一点，所以我在xml中配置了工厂
 */
public class BeanFactory {
    @Resource(name="BookServiceImpl")
    private BookService bookService;
    @Resource(name="transactionManager")
    private TransactionManager transactionManager;

    public BookService getBookService(){
        return (BookService) Proxy.newProxyInstance(bookService.getClass().getClassLoader(), bookService.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object returnValue=null;
                try{
                    transactionManager.beginTransaction();//开始事务
                    returnValue=method.invoke(bookService,args);
                    transactionManager.commit();
                    return returnValue;
                }catch(Exception e){
                    e.printStackTrace();
                    transactionManager.rollback();
                }finally {
                    transactionManager.release();
                }
                return null;
            }
        });
    }

}
