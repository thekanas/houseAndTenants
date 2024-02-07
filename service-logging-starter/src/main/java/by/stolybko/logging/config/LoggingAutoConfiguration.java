package by.stolybko.logging.config;

import by.stolybko.logging.aop.LogServiceAspect;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnClass(LoggingAutoConfiguration.class)
public class LoggingAutoConfiguration {

    @PostConstruct
    void init() {
        log.info("LoggingAutoConfiguration initialized");
    }

    @Bean
    public LogServiceAspect logServiceAspect() {
        return new LogServiceAspect();
    }
}
