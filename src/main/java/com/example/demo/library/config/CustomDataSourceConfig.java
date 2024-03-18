package com.example.demo.library.config;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnProperty(name = "app.datasource.custom.enable", havingValue = "true", matchIfMissing = false)
public class CustomDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "app.datasource.custom") // Prefijo para las propiedades de HikariCP
    public HikariDataSource dataSource(){
        return new HikariDataSource();
    }

}