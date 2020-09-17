package com.lms.dao.daoimpl;

import com.lms.bean.User;
import com.lms.dao.UserDao;
import com.lms.utils.ConnectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

@Repository(value="UserDao")
public class UserDaoImpl implements UserDao{

    /**
     * 使用SpringAPI事务处理时注释掉
     *
     @Autowired
    QueryRunner runner;

    @Resource(name="connectionUtils")
    private ConnectionUtils connectionUtils;

    public List<User> selectAll(){
        //QueryRunner runner = new QueryRunner(c3p0Utils.getDataSource());
        //注意query的第一个参数
        try {
            return runner.query(connectionUtils.getThreadConnection(),"select * from user", new BeanListHandler<User>(User.class));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public User selectById(Integer userId) {
        //QueryRunner runner = new QueryRunner(c3p0Utils.getDataSource());
        try {
            return runner.query(connectionUtils.getThreadConnection(),"select * from user where user_uid = ?", new BeanHandler<User>(User.class),userId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateBorrowNum(User user) {
        //QueryRunner runner = new QueryRunner(c3p0Utils.getDataSource());
        try {
            runner.update(connectionUtils.getThreadConnection(),"update user set user_borrnum = ? where user_uid = ?", user.getUser_borrnum(),user.getUser_uid());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    */

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<User> selectAll() {
        List<User> userList=jdbcTemplate.query("select * from user",new BeanPropertyRowMapper<User>(User.class));
        return userList.isEmpty()?null:userList;
    }

    @Override
    public User selectById(Integer userId) {
        List<User> userList=jdbcTemplate.query("select * from user where user_uid = ?",new BeanPropertyRowMapper<User>(User.class),userId);
        return userList.isEmpty()?null:userList.get(0);
    }

    @Override
    public void updateBorrowNum(User user) {
        jdbcTemplate.update("update user set user_borrnum = ? where user_uid = ?",user.getUser_borrnum(),user.getUser_uid());
    }

}
