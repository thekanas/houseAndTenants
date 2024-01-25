package by.stolybko;

import by.stolybko.database.dto.response.BaseResponseDTO;
import by.stolybko.database.dto.response.PersonResponseDTO;

import by.stolybko.service.cache.Cache;
import by.stolybko.service.cache.CacheFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class ApplicationRunner {

    @Value("${cache.algorithm}")
    private String algorithm;

    @Value("${cache.capacity}")
    private String capacity;

    @Bean
    public Cache<UUID, BaseResponseDTO> cache() {
        return CacheFactory.getCache(algorithm, Integer.parseInt(capacity));
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunner.class, args);
    }
}
