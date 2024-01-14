package by.stolybko.database.repository;

import by.stolybko.database.entity.HouseEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class HouseRepository extends RepositoryBase<UUID, HouseEntity> {

    public HouseRepository() {
        super(HouseEntity.class);
    }
}
