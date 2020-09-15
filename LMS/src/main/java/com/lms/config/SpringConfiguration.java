package com.lms.config;

import com.lms.service.BookService;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 *
 * ####为了测试该类，我把xml配置文件中关于QueryRunner和DataSource的注释了####
 *
 * 该类是一个配置类，它的作用和bean.xml是一样的
 * Configuration
 *   作用：指定当前类是一个配置类
 * ComponentScan
 *   作用：用于通过注解指定spring创建容器时要扫描的包
 *   属性：value：和basePackages的作用是一样的，指定创建容器时要扫描的包
 *               使用此注解就等于在xml中配置了：
 *               <context:component-scan base-package="com.lms"></context:component-scan>
 * Bean
 *   作用：把当前方法的返回值作为bean对象存入spring的ioc容器中
 *   属性：
 *     name:用于指定bean的id,有默认值
 *
 */
@Configuration
@ComponentScan(basePackages = "com.lms")//类似于xml的包扫描
public class SpringConfiguration {

    /**
     * 用于创建一个QueryRunner对象
     * @param dataSource
     * @return
     */
    @Bean(name="runner")
    @Scope("prototype")//变成单例的
    public QueryRunner createQueryRunner(DataSource dataSource){
        return new QueryRunner(dataSource);
    }


    /**
     * 创建数据源对象
     * @return
     */
    @Bean(name="DataSource")
    public DataSource createDataSource(){
        ComboPooledDataSource comboPooledDataSource=new ComboPooledDataSource();
        try {
            comboPooledDataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
            comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/lms?serverTimezone=GMT");
            comboPooledDataSource.setUser("root");
            comboPooledDataSource.setPassword("mysql2020");
            return comboPooledDataSource;
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return null;
    }
}
