package com.atech.pma.config.dbconfigs;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@EnableJpaRepositories(basePackages = "com.atech.pma.repository.mssql",
                       entityManagerFactoryRef = "mssqlEntityManagerFactoryBean",
                       transactionManagerRef = "mssqlTransactionManager")
public class MSSQLDatabaseConfiguration {

    @Bean
    @ConfigurationProperties("spring.mssql.datasource")
    public DataSourceProperties mssqlDatasourceProperties(){

        return new DataSourceProperties();
    }

    @Bean
    public DataSource mssqlDataSource(@Qualifier("mssqlDatasourceProperties") DataSourceProperties mssqlDatasourceProperties){

        return mssqlDatasourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean mssqlEntityManagerFactoryBean(@Qualifier("mssqlDataSource") DataSource mssqlDataSource,
                                                                                EntityManagerFactoryBuilder builder){

        return builder.dataSource(mssqlDataSource)
                .packages("com.atech.pma.entity.mssql")
                .persistenceUnit("mssql")
                .build();
    }

    @Bean
    public PlatformTransactionManager mssqlTransactionManager(
            @Qualifier("mssqlEntityManagerFactoryBean") LocalContainerEntityManagerFactoryBean mssqlEntityManagerFactoryBean){

        return new JpaTransactionManager(mssqlEntityManagerFactoryBean.getObject());
    }
}
