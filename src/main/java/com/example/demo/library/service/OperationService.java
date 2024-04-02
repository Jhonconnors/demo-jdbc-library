package com.example.demo.library.service;


import com.example.demo.library.model.EntityError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "app.custom.datasource.enable", havingValue = "DATABASE")
public class OperationService {

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    public void insertToDatabase(String table, EntityError error) {
        if ("DATABASE".equals(System.getProperty("app.custom.datasource.enable"))) {
            if (jdbcTemplate != null) {
                String sql = "INSERT INTO " + table + "(name, errorType, date) VALUES(?, ?, ?)";
                jdbcTemplate.update(sql, error.getName(), error.getErrorType(), error.getDate());
            } else {
                System.out.println("No se ha podido configurar Base de datos");
            }
        } else {
            System.out.println("Base de datos desactivada. No se realizaron inserciones.");
        }
    }
}

