package by.stolybko.database.repository;

import by.stolybko.database.entity.HouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HouseRepository extends JpaRepository<HouseEntity, Long> {

    Optional<HouseEntity> findHouseEntityByUuid(UUID houseUuid);
    void deleteHouseEntityByUuid(UUID houseUuid);
    List<HouseEntity> findAllByOwners_Uuid(UUID personUuid);
}


