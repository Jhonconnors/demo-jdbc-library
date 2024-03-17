package com.example.demo.library.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty(name = "app.datasource.enable", havingValue = "true", matchIfMissing = false)
public class CustomDataSourceConfig {


    @Bean
    @ConfigurationProperties(prefix = "app.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

}
