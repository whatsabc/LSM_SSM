package com.lms.dao.daoimpl;

import com.lms.bean.Book;
import com.lms.dao.BookDao;
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
import java.util.ArrayList;
import java.util.List;

@Repository(value="bookDao")
public class BookDaoImpl implements BookDao {

    /**
     * 使用SpringAPI事务处理时注释掉
     *
    @Autowired
    QueryRunner runner;

    @Resource(name="connectionUtils")
    private ConnectionUtils connectionUtils;

    @Override
    public Book selectByISBN(String ISBN) {
        //QueryRunner runner = new QueryRunner(c3p0Utils.getDataSource());
        try {
            return runner.query(connectionUtils.getThreadConnection(),"select * from book where book_isbn= ?", new BeanHandler<Book>(Book.class),ISBN);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    public void updateSurplus(Book book){
        //QueryRunner runner = new QueryRunner(c3p0Utils.getDataSource());
        try {
            runner.update(connectionUtils.getThreadConnection(),"UPDATE book SET book_surplus = ? WHERE book_isbn = ?",book.getBook_surplus(),book.getBook_isbn());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    */

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Book selectByISBN(String ISBN) {
        List<Book> bookList=jdbcTemplate.query("select * from book where book_isbn= ?",new BeanPropertyRowMapper<Book>(Book.class),ISBN);
        return bookList.isEmpty()?null:bookList.get(0);
    }

    @Override
    public void updateSurplus(Book book){
        jdbcTemplate.update("UPDATE book SET book_surplus = ? WHERE book_isbn = ?",book.getBook_surplus(),book.getBook_isbn());
    }
}
