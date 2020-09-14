package com.lms.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * 手动实现事务控制器
 * 事务管理相关的工具类，包含了开启事务，开启事务，提交事务，回滚事务和释放连接
 * @author Jianshu
 * @time 20200914
 */
@Repository(value="transactionManager")
public class TransactionManager {

    @Resource(name="connectionUtils")
    private ConnectionUtils connectionUtils;

    /**
     * 开始事务
     */
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
    public void release(){
        try {
            connectionUtils.getThreadConnection().close();//还回连接池
            connectionUtils.removeConnection();//进行解绑
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
