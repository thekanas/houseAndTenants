package by.stolybko.service.cache;


import by.stolybko.database.dto.response.BaseResponseDTO;
import by.stolybko.database.dto.response.PersonResponseDTO;

import by.stolybko.service.cache.impl.LFUCache;
import by.stolybko.service.cache.impl.LRUCache;
import org.springframework.beans.factory.annotation.Lookup;

import java.util.UUID;

public class CacheFactory {


    /**
     * Возвращает реализацию алгоритма кеширования
     * по его названию.
     *
     * @param name название алгоритма кеширования.
     * @param capacity максимальное кол-во кэшируемых объектов.
     * @return объект реализующий алгоритм кеширования.
     */
    @Lookup
    public static Cache<UUID, BaseResponseDTO> getCache(String name, Integer capacity) {
        return switch (name) {
            case "LRU" -> new LRUCache<>(capacity);
            case "LFU" -> new LFUCache<>(capacity);
            default -> null;
        };
    }
}
