package by.stolybko.config;

import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@PropertySource("classpath:application.yml")
public class LiquibaseConfig {

    @Value("${liquibase.changelog}")
    private String changelog;

    @Value("${liquibase.databaseAutoInitialization}")
    private boolean databaseAutoInitialization;

    private final DataSource dataSource;

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(changelog);
        liquibase.setDataSource(dataSource);
        liquibase.setShouldRun(databaseAutoInitialization);
        return liquibase;
    }
}
