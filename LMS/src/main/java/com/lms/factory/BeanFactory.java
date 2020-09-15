package com.lms.factory;

import com.lms.bean.Book;
import com.lms.bean.Record;
import com.lms.bean.User;
import com.lms.service.BookService;
import com.lms.utils.TransactionManager;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Calendar;
import java.util.Date;

/**
 * 实现事务控制
 * 这个是使用代理类手动实现的事务控制
 * @author Jianshu
 */

/**
 * 后续要调试SpringAPI提供的事务控制，把手动实现的先注释掉
 *
@Component(value="beanFactory")
public class BeanFactory {

    @Resource(name="BookServiceImpl")
    private BookService bookService;
    @Resource(name="transactionManager")
    private TransactionManager transactionManager;

    @Bean("proxyBookService")
    public BookService getBookService(){
        return (BookService) Proxy.newProxyInstance(bookService.getClass().getClassLoader(), bookService.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object returnValue=null;
                try{
                    transactionManager.beginTransaction();//相当于前置通知
                    returnValue=method.invoke(bookService,args);
                    transactionManager.commit();//相当于后置通知
                    return returnValue;
                }catch(Exception e){
                    e.printStackTrace();
                    transactionManager.rollback();//相当于异常通知
                }finally {
                    transactionManager.release();//相当于最终通知
                }
                return null;
            }
        });
    }

}
*/