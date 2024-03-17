package com.example.demo.library.persistence;

import com.example.demo.library.model.EntityError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
@ConditionalOnProperty(name = "app.datasource.enable", havingValue = "true", matchIfMissing = false)
public class ErrorManagementPersistence {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ErrorManagementPersistence(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertErrorToDatabase(String table, EntityError error){
        String sql = "INSERT INTO "+ table+"(id, name, errorType, date) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(sql, error.getId(), error.getName(), error.getErrorType(), error.getDate() );
    }
}
