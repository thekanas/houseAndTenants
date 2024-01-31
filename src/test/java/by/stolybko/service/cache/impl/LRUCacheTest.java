package by.stolybko.service.cache.impl;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


class LRUCacheTest {

    @Test
    void getFromCacheTest() {
        // given
        LRUCache<Long, String> cache = new LRUCache<>(3);
        cache.putInCache(1L, "first");
        cache.putInCache(2L, "second");
        cache.putInCache(3L, "third");

        // when
        Optional<String> actual1 = cache.getFromCache(1L);

        // then
        assertThat(actual1)
                .isPresent()
                .contains("first");

        // when
        cache.putInCache(4L, "fourth");
        Optional<String> actual2 = cache.getFromCache(2L);

        // then
        assertThat(actual2).isEmpty();
    }

    @Test
    void putInCacheTest() {
        // given
        LRUCache<Long, String> cache = new LRUCache<>(3);
        cache.putInCache(1L, "first");
        cache.putInCache(2L, "second");
        cache.putInCache(3L, "third");

        // when
        cache.putInCache(1L, "first+");
        Optional<String> actual = cache.getFromCache(1L);

        // then
        assertThat(actual)
                .isPresent()
                .contains("first+");
    }

    @Test
    void removeFromCacheTest() {
        // given
        LRUCache<Long, String> cache = new LRUCache<>(3);
        cache.putInCache(1L, "first");

        // when
        cache.removeFromCache(1L);
        Optional<String> actual = cache.getFromCache(1L);

        // then
        assertThat(actual).isEmpty();
    }

}