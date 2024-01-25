package by.stolybko.database.repository;

import by.stolybko.database.entity.HouseEntity;
import by.stolybko.database.entity.HouseHistory;
import by.stolybko.database.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseHistoryRepository extends JpaRepository<HouseHistory, Long> {

        List<HouseHistory> findPersonsByHouse(HouseEntity house);
        List<HouseHistory> findHousesByPerson(PersonEntity person);
}
