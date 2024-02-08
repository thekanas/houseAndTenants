package by.stolybko.config;

import by.stolybko.handler.AppExceptionHandler;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnClass(HandlerAutoConfiguration.class)
public class HandlerAutoConfiguration {

    @PostConstruct
    void init() {
        log.info("HandlerAutoConfiguration initialized");
    }

    @Bean
    public AppExceptionHandler exceptionHandler() {
        return new AppExceptionHandler();
    }

}
