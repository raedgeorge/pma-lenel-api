package com.atech.pma.config.dbconfigs;

import com.zaxxer.hikari.HikariDataSource;
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

import javax.sql.DataSource;

/**
 * @author raed abu Sa'da
 * on 17/04/2023
 */

@Configuration
@EnableJpaRepositories(basePackages = "com.atech.pma.repository.mysql",
                       entityManagerFactoryRef = "mysqlEntityManagerFactory",
                       transactionManagerRef = "mysqlTransactionManager")
public class MySQLDatabaseConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties("spring.mysql.datasource")
    public DataSourceProperties mysqlDataSourceProperties(){

        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource mysqlDataSource(@Qualifier("mysqlDataSourceProperties") DataSourceProperties mysqlDataSourceProperties){

        return mysqlDataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(@Qualifier("mysqlDataSource") DataSource mysqlDataSource,
                                                                            EntityManagerFactoryBuilder builder){

        return builder.dataSource(mysqlDataSource)
                .packages("com.atech.pma.entity.mysql")
                .persistenceUnit("mysql")
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager mysqlTransactionManager(
            @Qualifier("mysqlEntityManagerFactory") LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory){

        return new JpaTransactionManager(mysqlEntityManagerFactory.getObject());
    }
}
