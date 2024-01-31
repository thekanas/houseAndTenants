package by.stolybko.database.repository;

import by.stolybko.database.entity.HouseEntity;
import by.stolybko.database.entity.HouseHistory;
import by.stolybko.database.entity.PersonEntity;
import by.stolybko.database.entity.enam.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseHistoryRepository extends JpaRepository<HouseHistory, Long> {

        List<HouseHistory> findPersonsByHouseAndType(HouseEntity house, Type type);
        List<HouseHistory> findHousesByPersonAndType(PersonEntity person, Type type);
}
