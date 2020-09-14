package com.lms.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 连接的工具类，它用于从数据源中获取一个连接，并且实现和线程的绑定
 * 需要使用ThreadLocal对象把Connection和当前线程绑定，从而使一个线程中只能和一个事务绑定
 */
@Repository(value="connectionUtils")
public class ConnectionUtils {

    private ThreadLocal<Connection> threadLocal=new ThreadLocal<>();

    @Autowired
    private DataSource dataSource;//注入数据源

    /**
     * 获取当前线程上的连接
     * @return
     */
    public Connection getThreadConnection(){
        //1.先从ThreadLocal上获取
        Connection connection=threadLocal.get();
        //2.判断当前线程上是否有连接
        if(connection==null){
            //3.从数据源中获取一个连接，并存入ThreadLocal中
            try {
                connection=dataSource.getConnection();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            threadLocal.set(connection);
        }
        return connection;
    }

    /**
     * 把连接和线程解绑
     */
    public void removeConnection(){
        threadLocal.remove();
    }
}
