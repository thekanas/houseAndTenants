package by.stolybko.database.repository;

import by.stolybko.database.entity.HouseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class HouseRepository extends RepositoryBase<UUID, HouseEntity> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public HouseRepository() {
        super(HouseEntity.class);
    }

    @Override
    public List<HouseEntity> findAll() {

        return jdbcTemplate.query("SELECT * FROM house", new HouseMapper());

    }
}


