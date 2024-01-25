package by.stolybko.database.repository;

import by.stolybko.database.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    Optional<PersonEntity> findPersonEntityByUuid(UUID personUuid);
    void deletePersonEntityByUuid(UUID personUuid);
    List<PersonEntity> findPersonEntitiesByHouseUuid(UUID houseUuid);

}
