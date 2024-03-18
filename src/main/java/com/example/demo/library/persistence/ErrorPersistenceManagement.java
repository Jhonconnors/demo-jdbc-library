package com.example.demo.library.persistence;


import com.example.demo.library.model.EntityError;
import jakarta.annotation.PostConstruct;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;

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

    @ConfigurationProperties(prefix = "app.datasource.custom") // Prefijo para las propiedades de HikariCP
    public DataSource dataSource(){
        return new BasicDataSource();
    }
}