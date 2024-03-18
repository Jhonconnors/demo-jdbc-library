package com.example.demo.library.persistence;

import com.example.demo.library.model.EntityError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
@ConditionalOnProperty(name = "app.datasource.custom.enable", havingValue = "DATABASE", matchIfMissing = false)
public class ErrorPersistenceManagement {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ErrorPersistenceManagement(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertErrorToDatabase(String table, EntityError error){
        String sql = "INSERT INTO "+ table+"(name, errorType, date) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, error.getName(), error.getErrorType(), error.getDate() );
    }
}