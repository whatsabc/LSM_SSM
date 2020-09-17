package com.lms.dao.daoimpl;

import com.lms.bean.Record;
import com.lms.dao.RecordDao;
import com.lms.utils.ConnectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.SQLException;

@Repository(value="RecordDao")
//@Scope("singleton")
public class RecordDaoImpl implements RecordDao {

    /**
     * 使用SpringAPI事务处理时注释掉
     *
    @Autowired
    QueryRunner runner;

    @Resource(name="connectionUtils")
    private ConnectionUtils connectionUtils;

    @Override
    public void insert(Record record) {
        //QueryRunner runner = new QueryRunner(c3p0Utils.getDataSource());
        try{
            runner.update(connectionUtils.getThreadConnection(),"insert into record (record_loandate, record_duedate, record_muclt, user_uid, book_isbn)  values(?,?,?,?,?)" ,
                    record.getRecord_loandate(),
                    record.getRecord_duedate(),
                    record.getRecord_muclt(),
                    record.getUser_uid(),
                    record.getBook_isbn()
            );
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    */

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void insert(Record record) {
        jdbcTemplate.update("insert into record (record_loandate, record_duedate, record_muclt, user_uid, book_isbn)  values(?,?,?,?,?)",
                record.getRecord_loandate(),
                record.getRecord_duedate(),
                record.getRecord_muclt(),
                record.getUser_uid(),
                record.getBook_isbn()
        );
    }
}
