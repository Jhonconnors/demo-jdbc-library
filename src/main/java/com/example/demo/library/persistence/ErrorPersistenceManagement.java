package com.example.demo.library.persistence;

import com.example.demo.library.model.EntityError;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Component
public class ErrorPersistenceManagement {

    private final JdbcTemplate jdbcTemplate;

    @Value("${app.datasource.custom.enable:TOPIC}")
    private String enable;

    public ErrorPersistenceManagement(){
        if (enable.equals("DATABASE")){
            this.jdbcTemplate = new JdbcTemplate(dataSource());
        } else {
            this.jdbcTemplate = null;
        }

    }

    //@ConfigurationProperties(prefix = "app.datasource.custom") // Prefijo para las propiedades de HikariCP
    public DataSource dataSource(){
        if (enable.equals("DATABASE")){
            return new BasicDataSource();
        }
        return DataSourceBuilder.create().build();
    }

    public void insertErrorToDatabase(String table, EntityError error){
        String sql = "INSERT INTO "+ table+"(name, errorType, date) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, error.getName(), error.getErrorType(), error.getDate() );
    }
}