package com.hackathon.namepronunciationtool.config;

import com.hackathon.namepronunciationtool.repo.UserYsqlRepository;
import com.yugabyte.data.jdbc.datasource.YugabyteTransactionManager;
import com.yugabyte.data.jdbc.repository.config.AbstractYugabyteJdbcConfiguration;
import com.yugabyte.data.jdbc.repository.config.EnableYsqlRepositories;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableYsqlRepositories(basePackageClasses = UserYsqlRepository.class)
public class YsqlConfig extends AbstractYugabyteJdbcConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        Properties poolProperties = new Properties();
        poolProperties.setProperty("dataSourceClassName", environment.getProperty("yugabyte.datasource.class"));
        poolProperties.setProperty("dataSource.serverName", environment.getProperty("yugabyte.host"));
        poolProperties.setProperty("dataSource.portNumber", environment.getProperty("yugabyte.port"));
        poolProperties.setProperty("dataSource.user", environment.getProperty("yugabyte.userName"));
        poolProperties.setProperty("dataSource.password", environment.getProperty("yugabyte.password"));
        poolProperties.setProperty("dataSource.additionalEndpoints", environment.getProperty("yugabyte.additionalEndpoints"));

        HikariConfig hikariConfig = new HikariConfig(poolProperties);
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    @Autowired
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcOperations namedParameterJdbcOperations(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public TransactionManager transactionManager(DataSource dataSource) {
        return new YugabyteTransactionManager(dataSource);
    }
}
