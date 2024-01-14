package by.stolybko.database.repository;

import by.stolybko.database.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Repository<K extends Serializable, E extends BaseEntity<K>> {

    void save(E entity);

    void delete(K uuid);

    void update(E entity);

    Optional<E> findByUuid(K uuid);

    List<E> findAll();

}
