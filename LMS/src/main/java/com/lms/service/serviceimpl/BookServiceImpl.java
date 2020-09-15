package com.lms.service.serviceimpl;

import com.lms.bean.Book;
import com.lms.bean.Record;
import com.lms.bean.User;
import com.lms.dao.BookDao;
import com.lms.dao.RecordDao;
import com.lms.dao.UserDao;
import com.lms.dao.daoimpl.BookDaoImpl;
import com.lms.dao.daoimpl.RecordDaoImpl;
import com.lms.dao.daoimpl.UserDaoImpl;
import com.lms.service.BookService;
import com.lms.utils.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

/**
 * 账户的业务层
 *
 */

/**
 * 曾经的xml的配置
 * <bean id="BookService" class="com.lms.service.serviceimpl.BookServiceImpl">
 *     scope="" init-method="" destroy-method="">
 *     <property name="" value="" | ref=""></property>
 *     </bean>
 * 用于创建对象的
 *  和xml中bean标签实现的功能是一样的
 *    Component:
 *      作用：把当前类对象存入spring容器中
 *      属性：value 用于指定bean的id。当我们不写时，默认值是当前类名改小写
 *    Controller：一般用在表现层
 *    Service：一般用在业务层
 *    Repository：一般用在持久层
 *
 * 用于注入数据的
 *  它们的作用就和xml配置文件中bean标签property标签的作用是一样的
 *  AutoWrite
 *    作用：自动按照类型注入，如果IOC有多个容器时，
 *    出现位置：可以是变量上，也可以是方法上
 *    细节：在使用注解注入时，set方法就不是必须的
 *  Qualifier
 *    作用：再按照类中注入的基础之上再按照名称注入。他在给类成员注入时不能单独使用。但是在给方法参数注入时可以
 *    属性：
 *      value：用于指定注入bean的id
 *
 *
 *  Resource
 *    作用：直接按照bean的id注入。他可以独立使用
 *    属性：
 *      name:用于指定bean的id
 *
 *  ####以上三个注入都只能注入其他bean类型的数据，而基本类型和String类型无法使用上述注解实现
 *  ####另外，集合类型的注入只能通过XML来实现
 *
 *  Value
 *    作用：用于注入基本类型和String类型的数据
 *    属性：
 *      value：用于指定数据的值。它可以使用spring的SpEL(也就是spring的el表达式)
 *
 * 用于改变作用范围的
 *  和xml bean标签中scope属性的作用是一样
 *  Scope
 *    作用：用于指定的bean的作用范围
 *    属性：value：指定范围的取值。常用取值，singleton prototype
 *
 * 和生命周期相关的
 *  和bean标签中使用init-method和destroy-method的作用是一样的
 *  PreDestroy
 *    作用：用于指定销毁方法
 *  PostConstruct
 *    作用：用于指定初始化方法
 */
@Component(value="BookServiceImpl")
@Transactional
public class BookServiceImpl implements BookService {

    /**
     * 用来检测生命周期执行方法的执行情况
    //@PostConstruct
    public void init(){
        System.out.println("init()");
    }

    //@PreDestroy
    public void destroy(){
        System.out.println("destroy()");
    }
    */

    /**
     * 在进行注入数据时，我们可以使用两种方式来进行注入（AutoWrite和Resource）
     */
    @Resource(name="UserDao")
    private UserDao userDao;

    @Resource(name="BookDao")
    private BookDao bookDao;

    @Resource(name="RecordDao")
    private RecordDao recordDao;

    /**
     * ####当我们不使用任何代理，我们就需要手动注入事务控制器，然后在代码中直接编码进行事务控制

    @Resource(name="transactionManager")
    private TransactionManager transactionManager;//事务控制器
    */

    /**
     * 下面这个结束操作是我们非代理模式和自己的事务控制实现的事务控制
     * 可以看到很麻烦，所以我们先抽象公共部分进行代理模式简化
     * 用到了以下几个类
     * ---ConnectionUtils
     * ---TransactionManager
     * @param userId
     * @param ISBN
     * @return
     *
    @Override
    public String bookBorrow(int userId, String ISBN) {
        try{
            transactionManager.beginTransaction();//开始事务

            User user=userDao.selectById(userId);
            //查询读者情况
            if(user.getUser_borrnum()>=user.getUser_maxnum()){
                return "读者借书超过上限";
            }
            //查询图书情况
            Book book=bookDao.selectByISBN(ISBN);
            if(book.getBook_surplus()<=0){
                return "该图书库存为0";
            }
            //更改读者已经借阅数量
            user.setUser_borrnum(user.getUser_borrnum()+1);
            userDao.updateBorrowNum(user);
            int a=10/0;//在这里制造一个异常，来体现事务的重要性
            //更改图书剩余数量
            book.setBook_surplus(book.getBook_surplus()-1);
            bookDao.updateSurplus(book);
            //生成借阅表
            Calendar c= Calendar.getInstance();
            Date loanDate=Calendar.getInstance().getTime();
            c.add(Calendar.DAY_OF_MONTH,30);
            Date dueDate=c.getTime();
            recordDao.insert(new Record(loanDate,dueDate,0.1f,userId,ISBN));

            transactionManager.commit();
            return "借阅成功";
        }catch(Exception e){
            e.printStackTrace();
            transactionManager.rollback();
        }finally {
            transactionManager.release();
        }
        return null;
    }
    */

    /**
     * 这个类是我们通过代理模式和自己的事务控制实现的事务控制
     * 用到了以下几个类
     * ---BeanFactory
     *   ---ConnectionUtils
     *   ---TransactionManager
     * @param userId
     * @param ISBN
     * @return

    @Override
    public String proxyBookBorrow(int userId, String ISBN) {

        User user=userDao.selectById(userId);
        //查询读者情况
        if(user.getUser_borrnum()>=user.getUser_maxnum()){
            return "读者借书超过上限";
        }
        //查询图书情况
        Book book=bookDao.selectByISBN(ISBN);
        if(book.getBook_surplus()<=0){
            return "该图书库存为0";
        }
        //更改读者已经借阅数量
        user.setUser_borrnum(user.getUser_borrnum()+1);
        userDao.updateBorrowNum(user);
        int a=10/0;//在这里制造一个异常，来体现事务的重要性
        //更改图书剩余数量
        book.setBook_surplus(book.getBook_surplus()-1);
        bookDao.updateSurplus(book);
        //生成借阅表
        Calendar c= Calendar.getInstance();
        Date loanDate=Calendar.getInstance().getTime();
        c.add(Calendar.DAY_OF_MONTH,30);
        Date dueDate=c.getTime();
        recordDao.insert(new Record(loanDate,dueDate,0.1f,userId,ISBN));

        return "借阅成功";
    }
    */

    /**
     * 这个类是我们通过AOP和自己的事务控制实现的事务控制
     * 用到了以下几个类
     *  ---ConnectionUtils
     *  ---TransactionManager
     * @param userId
     * @param ISBN
     * @return

    @Override
    @Transactional
    public String springAOPBookBorrow(int userId, String ISBN) {

        User user=userDao.selectById(userId);
        //查询读者情况
        if(user.getUser_borrnum()>=user.getUser_maxnum()){
            return "读者借书超过上限";
        }
        //查询图书情况
        Book book=bookDao.selectByISBN(ISBN);
        if(book.getBook_surplus()<=0){
            return "该图书库存为0";
        }
        //更改读者已经借阅数量
        user.setUser_borrnum(user.getUser_borrnum()+1);
        userDao.updateBorrowNum(user);
        int a=10/0;//在这里制造一个异常，来体现事务的重要性
        //更改图书剩余数量
        book.setBook_surplus(book.getBook_surplus()-1);
        bookDao.updateSurplus(book);
        //生成借阅表
        Calendar c= Calendar.getInstance();
        Date loanDate=Calendar.getInstance().getTime();
        c.add(Calendar.DAY_OF_MONTH,30);
        Date dueDate=c.getTime();
        recordDao.insert(new Record(loanDate,dueDate,0.1f,userId,ISBN));

        return "借阅成功";
    }
    */

    /**
     * 这个类是我们通过AOP和SpringAPI实现的事务控制
     * 用到了以下几个类
     *  ---ConnectionUtils
     * @param userId
     * @param ISBN
     * @return
     */
    @Override
    public String springAPIBookBorrow(int userId, String ISBN) {

        User user=userDao.selectById(userId);
        //查询读者情况
        if(user.getUser_borrnum()>=user.getUser_maxnum()){
            return "读者借书超过上限";
        }
        //查询图书情况
        Book book=bookDao.selectByISBN(ISBN);
        if(book.getBook_surplus()<=0){
            return "该图书库存为0";
        }
        //更改读者已经借阅数量
        user.setUser_borrnum(user.getUser_borrnum()+1);
        userDao.updateBorrowNum(user);
        int a=10/0;//在这里制造一个异常，来体现事务的重要性
        //更改图书剩余数量
        book.setBook_surplus(book.getBook_surplus()-1);
        bookDao.updateSurplus(book);
        //生成借阅表
        Calendar c= Calendar.getInstance();
        Date loanDate=Calendar.getInstance().getTime();
        c.add(Calendar.DAY_OF_MONTH,30);
        Date dueDate=c.getTime();
        recordDao.insert(new Record(loanDate,dueDate,0.1f,userId,ISBN));

        return "借阅成功";
    }
}
