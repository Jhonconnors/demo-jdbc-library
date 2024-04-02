package com.example.demo.library.persistence;

import com.example.demo.library.model.EntityError;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
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

    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public JdbcTemplate jdbcTemplate(){
        if ("DATABASE".equals(enable)){
            jdbcTemplate = new JdbcTemplate(dataSource());
        } else{
            return jdbcTemplate = null;
        }
        return jdbcTemplate;
    }


    public DataSource dataSource(){
        if (username !=null && url != null && password != null && driver != null){
            HikariDataSource hikari = new HikariDataSource();
            hikari.setJdbcUrl(url);
            hikari.setUsername(username);
            hikari.setPassword(password);
            hikari.setDriverClassName(driver);
            hikari.setMaximumPoolSize(maxTotal);
            hikari.setMinimumIdle(maxIdle);
            hikari.setConnectionTimeout(maxWaitMillis);
            return hikari;
        } else {
            throw new RuntimeException("One o more Properties Connection is missing when enable=DATABASE");
        }


    }

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