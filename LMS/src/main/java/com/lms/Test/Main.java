package com.lms.Test;

import com.lms.config.SpringConfiguration;
import com.lms.dao.BookDao;
import com.lms.dao.RecordDao;
import com.lms.dao.UserDao;
import com.lms.service.BookService;
import com.lms.service.serviceimpl.BookServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args){
        //获取核心容器对象
        //ApplicationContext applicationContext=new ClassPathXmlApplicationContext("bean.xml");
        //根据id获取Bean对象
        //UserDao userDao=(UserDao)applicationContext.getBean("UserDao");
        //BookDao bookDao=(BookDao)applicationContext.getBean("BookDao");
        //注意以下这种写法，不使用强转
        //RecordDao recordDao=applicationContext.getBean("RecordDao",RecordDao.class);
        //BookService bookService=(BookService) applicationContext.getBean("BookServiceImpl");
        //bookService.BookBorrow(100000,"123456789");
        //System.out.println(bookService);

        /**
         * 用来检测Scope的单例和多例对象
         */
        //RecordDao recordDao=applicationContext.getBean("RecordDao",RecordDao.class);
        //RecordDao recordDao2=applicationContext.getBean("RecordDao",RecordDao.class);
        //System.out.println(recordDao==recordDao2);

        /**
         * 用来检测生命周期执行方法的执行情况
         * 需要注意的是，核心容器的关闭方法在他的父类中，所以我们需要创建它的父类对象，并进行调用
         * ####以下是控制台输出
         * init()
         * 九月 14, 2020 11:47:09 上午 com.mchange.v2.log.MLog
         * 信息: MLog clients using java 1.4+ standard logging.
         * 九月 14, 2020 11:47:10 上午 com.mchange.v2.c3p0.C3P0Registry
         * 信息: Initializing c3p0-0.9.5.2 [built 08-December-2015 22:06:04 -0800; debug? true; trace: 10]
         * 九月 14, 2020 11:47:11 上午 com.mchange.v2.c3p0.impl.AbstractPoolBackedDataSource
         * 信息: Initializing c3p0 pool... com.mchange.v2.c3p0.ComboPooledDataSource [ acquireIncrement -> 3, acquireRetryAttempts -> 30, acquireRetryDelay -> 1000, autoCommitOnClose -> false, automaticTestTable -> null, breakAfterAcquireFailure -> false, checkoutTimeout -> 0, connectionCustomizerClassName -> null, connectionTesterClassName -> com.mchange.v2.c3p0.impl.DefaultConnectionTester, contextClassLoaderSource -> caller, dataSourceName -> myc3p0, debugUnreturnedConnectionStackTraces -> false, description -> null, driverClass -> com.mysql.cj.jdbc.Driver, extensions -> {}, factoryClassLocation -> null, forceIgnoreUnresolvedTransactions -> false, forceSynchronousCheckins -> false, forceUseNamedDriverClass -> false, identityToken -> 1hgepz5ac12ho5lgq6q19a|50a7bc6e, idleConnectionTestPeriod -> 0, initialPoolSize -> 3, jdbcUrl -> jdbc:mysql://localhost:3306/lms?serverTimezone=GMT, maxAdministrativeTaskTime -> 0, maxConnectionAge -> 0, maxIdleTime -> 0, maxIdleTimeExcessConnections -> 0, maxPoolSize -> 15, maxStatements -> 0, maxStatementsPerConnection -> 0, minPoolSize -> 3, numHelperThreads -> 3, preferredTestQuery -> null, privilegeSpawnedThreads -> false, properties -> {user=******, password=******}, propertyCycle -> 0, statementCacheNumDeferredCloseThreads -> 0, testConnectionOnCheckin -> false, testConnectionOnCheckout -> false, unreturnedConnectionTimeout -> 0, userOverrides -> {}, usesTraditionalReflectiveProxies -> false ]
         * destroy()
         */
        //ClassPathXmlApplicationContext classPathXmlApplicationContext=new ClassPathXmlApplicationContext("bean.xml");
        //BookService bookService=(BookService) classPathXmlApplicationContext.getBean("BookServiceImpl");
        //bookService.BookBorrow(100000,"123456789");
        //classPathXmlApplicationContext.close();


        /**
         * 测试代码
         */
        //bookService.BookBorrow(100000,"123456789");

        /**
         * 不使用xml配置数据库和数据源时
         */
        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(SpringConfiguration.class);
        BookService bookService=(BookService) applicationContext.getBean("BookServiceImpl");
        bookService.BookBorrow(100000,"123456789");

    }
}