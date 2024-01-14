package by.stolybko.database.repository;

import by.stolybko.database.entity.BaseEntity;
import by.stolybko.exeption.EntityNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public abstract class RepositoryBase<K extends Serializable, E extends BaseEntity<K>> implements Repository<K, E> {

    private final Class<E> clazz;

    @Getter
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(E entity) {
        entityManager.persist(entity);
    }

    @Override
    public void delete(K uuid) {
        Optional<E> entity = findByUuid(uuid);
        if (entity.isPresent()) {
            entityManager.remove(entity.get());
            entityManager.flush();
        }
    }

    @Override
    public void update(E entity) {
        entityManager.merge(entity);
    }

    @Override
    public Optional<E> findByUuid(K uuid) {
        E singleResult = null;
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            var criteria = builder.createQuery(clazz);
            Root<E> root = criteria.from(clazz);
            criteria.select(root).where(builder.equal(root.get("uuid"), uuid));
            singleResult = entityManager.createQuery(criteria).getSingleResult();
        } catch (Exception e) {
            throw  EntityNotFoundException.of(clazz, uuid);
        }

        return Optional.ofNullable(singleResult);
    }

    @Override
    public List<E> findAll() {
        var criteria = entityManager.getCriteriaBuilder().createQuery(clazz);
        criteria.from(clazz);
        return entityManager.createQuery(criteria)
                .getResultList();
    }

}
