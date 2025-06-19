//package com.example.studentmanagement.configuration;
//
//import jakarta.persistence.EntityManagerFactory;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        basePackages = "com.example.studentmanagement.repository.postgres",
//        entityManagerFactoryRef = "postgresEntityManagerFactory",
//        transactionManagerRef = "postgresTransactionManager"
//)
//public class PostgresConfig {
//
//    @Primary
//    @Bean(name = "postgresDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource postgresDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Primary
//    @Bean(name = "postgresEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory(
//            EntityManagerFactoryBuilder builder,
//            @Qualifier("postgresDataSource") DataSource dataSource) {
//
//        return builder
//                .dataSource(dataSource)
//                .packages("com.example.studentmanagement.model.entity.postgres")
//                .persistenceUnit("postgres")
//                .build();
//    }
//
//    @Primary
//    @Bean(name = "postgresTransactionManager")
//    public PlatformTransactionManager postgresTransactionManager(
//            @Qualifier("postgresEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
//
//        return new JpaTransactionManager(entityManagerFactory);
//    }
//}
//
