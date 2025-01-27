package sspd.sms.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.*;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScans({
        @ComponentScan(basePackages = "sspd.sms.teacheroptions.db"),
        @ComponentScan(basePackages = "sspd.sms.courseoptions.db"),
        @ComponentScan(basePackages = "sspd.sms.classoptions.db")

})


public class AppConfig {




    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setMessageInterpolator(new org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator());
        return validatorFactoryBean;
    }

    @Bean
    public DataSource dataSource() {



        HikariConfig hikariConfig = new HikariConfig();

        // Database connection settings
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/smsdb");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("");
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");

        // HikariCP specific settings
        hikariConfig.setMaximumPoolSize(20);
        hikariConfig.setMinimumIdle(5);
        hikariConfig.setIdleTimeout(30000);
        hikariConfig.setConnectionTimeout(30000);
        hikariConfig.setMaxLifetime(600000);

        return new HikariDataSource(hikariConfig);
    }


//    @Bean
//    public LocalSessionFactoryBean sessionFactory() {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource());
//       // sessionFactory.setPackagesToScan("sspd.sms.teacheroptions");
//
//        sessionFactory.setPackagesToScan(
//                "sspd.sms.teacheroptions",
//                "sspd.sms.courseoptions"
//        );
//
//        sessionFactory.setConfigLocation(new org.springframework.core.io.ClassPathResource("hibernate.cfg.xml"));
//        return sessionFactory;


    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan(
                "sspd.sms.teacheroptions",
                "sspd.sms.courseoptions",
                "sspd.sms.classoptions"
        );
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.jdbc.batch_size", "100");
        properties.put("hibernate.c3p0.min_size", "5");
        properties.put("hibernate.c3p0.max_size", "20");
        properties.put("hibernate.c3p0.timeout", "300");
        properties.put("hibernate.c3p0.max_statements", "50");
        properties.put("hibernate.c3p0.driverClass", "com.mysql.cj.jdbc.Driver");
        properties.put("hibernate.transaction.jta.platform", "org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform");
        properties.put("hibernate.order_inserts", "true");
        properties.put("hibernate.order_updates", "true");


        return properties;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }
}