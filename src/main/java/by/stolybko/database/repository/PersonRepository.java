package by.stolybko.database.repository;

import by.stolybko.database.entity.PersonEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class PersonRepository extends RepositoryBase<UUID, PersonEntity> {

    public PersonRepository() {
        super(PersonEntity.class);
    }

}
