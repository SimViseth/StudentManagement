package com.example.studentmanagement.configuration;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.studentmanagement.repository.oracle",  // Tells Spring where to scan for JPA repository
        entityManagerFactoryRef = "oracleEntityManagerFactoryBean",
        transactionManagerRef = "oracleTransactionManager"
)

public class OracleConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.oracle")
    public DataSourceProperties oracleDatasourceProperties() {
        return new DataSourceProperties();
    }


    // Bind to our custom: url, username, password, driverClass
    @Bean
    public DataSource oracleDatasource() {
        return oracleDatasourceProperties().initializeDataSourceBuilder().build();
    }


    // Manage entity
    // @Qualifier: help spring to choose which one to inject when have multiple beans of the same type
    @Bean
    LocalContainerEntityManagerFactoryBean oracleEntityManagerFactoryBean(EntityManagerFactoryBuilder entityManagerFactoryBuilder,
                                                                         @Qualifier("oracleDatasource") DataSource dataSource) {

        Map<String, Object> jpaProperties = new HashMap<>();
        // hibernate update the DB schema
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");

        return entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages("com.example.studentmanagement.model.entity.oracle")  // Scans package for JPA entity classes
                .properties(jpaProperties)
                .build();
    }

    @Bean
    PlatformTransactionManager oracleTransactionManager(@Qualifier("oracleEntityManagerFactoryBean") LocalContainerEntityManagerFactoryBean emfb) {
        return new JpaTransactionManager(emfb.getObject());
    }
}
