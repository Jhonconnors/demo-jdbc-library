package com.example.demo.library.persistence;


import com.example.demo.library.model.EntityError;
import jakarta.annotation.PostConstruct;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class ErrorPersistenceManagement {

    private JdbcTemplate jdbcTemplate;

    @Value("${app.datasource.custom.enable:TOPIC}")
    private String enable;

    @PostConstruct
    public void init(){
        if (enable.equals("DATABASE")){
            this.jdbcTemplate = new JdbcTemplate(dataSource());
        } else {
            this.jdbcTemplate = null;
        }
    }
    public void insertErrorToDatabase(String table, EntityError error){
        String sql = "INSERT INTO "+ table+"(name, errorType, date) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, error.getName(), error.getErrorType(), error.getDate() );
    }

    @Value("${app.custom.datasource.url:#{null}}")
    private String url;

    @Value("${app.custom.datasource.username:#{null}}")
    private String username;

    @Value("${app.custom.datasource.password:#{null}}")
    private String password;

    @Value("${app.custom.datasource.driver:#{null}}")
    private String driver;

    @Value("${app.custom.datasource.maxTotal:15}")
    private int maxTotal;

    @Value("${app.custom.datasource.maxIdle:5}")
    private int maxIdle;

    @Value("${app.custom.datasource.maxWaitMillis:3000}")
    private long maxWaitMillis;

    public DataSource dataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        if (username !=null || url != null ||
                password != null || driver != null){
            dataSource.setUrl(this.url);
            dataSource.setUsername(this.username);
            dataSource.setPassword(this.password);
            dataSource.setDriverClassName(driver);
            dataSource.setMaxTotal(this.maxTotal);
            dataSource.setMaxIdle(this.maxIdle);
            dataSource.setMaxWaitMillis(this.maxWaitMillis);
        }

        return dataSource;
    }
}