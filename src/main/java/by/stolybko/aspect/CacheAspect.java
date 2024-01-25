package by.stolybko.aspect;


import by.stolybko.database.dto.response.BaseResponseDTO;

import by.stolybko.service.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Класс внедряет функционал кеширования
 * с помощью АОП.
 */
@Aspect
@Component
@RequiredArgsConstructor
public class CacheAspect {

    private final Cache<UUID, BaseResponseDTO> cache;

    /**
     * Метод отфильтровывает точки соединения
     * по названию класса.
     */
    @Pointcut("within(by.stolybko.service.impl.*ServiceImpl)")
    public void isServiceLayer() {
    }

    @Pointcut("isServiceLayer() && execution( * by.stolybko.service.impl.*.getByUuid(..))")
    public void getByUuidServiceMethod() {
    }

    @Pointcut("isServiceLayer() && execution( * by.stolybko.service.*.create(..))")
    public void createServiceMethod() {
    }

    @Pointcut("isServiceLayer() && execution( * by.stolybko.service.*.update(..))")
    public void updateServiceMethod() {
    }

    @Pointcut("isServiceLayer() && execution( * by.stolybko.service.*.delete(..))")
    public void deleteServiceMethod() {
    }

    /**
     * Возвращает объект из кэша при его наличии.
     * Иначе, возвращает объект
     * и помещает его в кэш.
     */
    @SuppressWarnings("unchecked")
    @Around(value = "getByUuidServiceMethod()")
    public Object cachingGetByUuid(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        UUID uuid = (UUID) proceedingJoinPoint.getArgs()[0];
        Optional<BaseResponseDTO> cachedObj = cache.getFromCache(uuid);

        if (cachedObj.isPresent()) {
            return cachedObj.get();
        } else {
            Optional<BaseResponseDTO> returnObj = Optional.ofNullable( (BaseResponseDTO) proceedingJoinPoint.proceed());
            returnObj.ifPresent(o -> cache.putInCache(uuid, o));

            return returnObj.get();

        }
    }

    /**
     * Помещает объект в кэш после его сохранения.
     */
    @SuppressWarnings("unchecked")
    @AfterReturning(value = "createServiceMethod()", returning = "result")
    public void cachingSave(Object result) {
        Optional<BaseResponseDTO> entity = (Optional<BaseResponseDTO>) result;
        entity.ifPresent(baseEntity -> cache.putInCache((UUID) baseEntity.getUuid(), baseEntity));
    }

    /**
     * Помещает объект в кэш после его обновления.
     */
    @SuppressWarnings("unchecked")
    @AfterReturning(value = "updateServiceMethod()", returning = "result")
    public void cachingUpdate(Object result) {
        Optional<BaseResponseDTO> entity = (Optional<BaseResponseDTO>) result;
        entity.ifPresent(baseEntity -> cache.putInCache((UUID) baseEntity.getUuid(), baseEntity));
    }

    /**
     * Удаляет объект из кэша после его удаления.
     */
    @AfterReturning(value = "deleteServiceMethod() && args(uuid) ", argNames = "uuid")
    public void cachingUpdate(UUID uuid) {
        cache.removeFromCache(uuid);
    }
}
