package com.example.demo.library.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
//@ConditionalOnProperty(name = "app.datasource.custom.enable", havingValue = "DATABASE", matchIfMissing = false)
public class CustomDataSourceConfig {

    @Value("${app.datasource.custom.enable}")
    private String enable;

    @Bean
    @ConfigurationProperties(prefix = "app.datasource.custom") // Prefijo para las propiedades de HikariCP
    public DataSource dataSource(){
        if (enable.equals("DATABASE")){
            return new BasicDataSource();
        }
        return DataSourceBuilder.create().build();
    }

}