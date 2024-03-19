package com.example.demo.library.persistence;

import com.example.demo.library.model.EntityError;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class ErrorPersistenceManagement {

    private JdbcTemplate jdbcTemplate;

    @Value("${app.datasource.custom.enable:TOPIC}")
    private String enable;

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

    @PostConstruct
    public void init(){
        if (enable.equals("DATABASE") && (username !=null || url != null ||
                password != null || driver != null)){
            this.jdbcTemplate = new JdbcTemplate(dataSource());
        } else {
            this.jdbcTemplate = null;
        }
    }
    public void insertErrorToDatabase(String table, EntityError error){
        String sql = "INSERT INTO "+ table+"(name, errorType, date) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, error.getName(), error.getErrorType(), error.getDate() );
    }

    public DataSource dataSource(){
        DataSource dataSource = DataSourceBuilder.create()
                .username(username)
                .url(url)
                .password(password)
                .driverClassName(driver)
                .build();
        return dataSource;
    }
}