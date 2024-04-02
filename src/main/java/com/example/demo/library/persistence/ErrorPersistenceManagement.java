package com.example.demo.library.persistence;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class ErrorPersistenceManagement {


    @Value("${app.custom.datasource.enable:TOPIC}")
    private String enable;

    @Value("${app.custom.datasource.url:#{null}}")
    private String url;

    @Value("${app.custom.datasource.username:#{null}}")
    private String username;

    @Value("${app.custom.datasource.password:#{null}}")
    private String password;

    @Value("${app.custom.datasource.driver-class-name:#{null}}")
    private String driver;

    @Value("${app.custom.datasource.maxTotal:15}")
    private int maxTotal;

    @Value("${app.custom.datasource.maxIdle:5}")
    private int maxIdle;

    @Value("${app.custom.datasource.maxWaitMillis:3000}")
    private long maxWaitMillis;


    @PostConstruct
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }


    public DataSource dataSource(){
        if (username !=null && url != null && password != null && driver != null){
            DataSource dataSource = DataSourceBuilder.create()
                    .url(url)
                    .username(username)
                    .password(password)
                    .driverClassName(driver).build();
            return dataSource;
        } else {
            throw new RuntimeException("One o more Properties Connection is missing when enable=DATABASE");
        }


    }

//    public DataSource dataSource(){
//        if (username !=null && url != null && password != null && driver != null){
//            DataSource dataSource = DataSourceBuilder.create()
//                    .url(url)
//                    .username(username)
//                    .password(password)
//                    .driverClassName(driver).build();
//            return dataSource;
//        } else {
//            throw new RuntimeException("One o more Properties Connection is missing when enable=DATABASE");
//        }
//
//
//    }

//    public DataSource dataSource(){
//        if (username !=null && url != null && password != null && driver != null){
//            BasicDataSource dataSource = new BasicDataSource();
//            dataSource.setUrl(url);
//            dataSource.setUsername(username);
//            dataSource.setPassword(password);
//            dataSource.setDriverClassName(driver);
//            return dataSource;
//        } else {
//            throw new RuntimeException("One o more Properties Connection is missing when enable=DATABASE");
//        }
//
//
//    }
}