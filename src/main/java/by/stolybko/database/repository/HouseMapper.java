package by.stolybko.database.repository;

import by.stolybko.database.entity.HouseEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


public class HouseMapper implements RowMapper<HouseEntity> {

    @Override
    public HouseEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        HouseEntity house = HouseEntity.builder()
                .id(rs.getLong("id"))
                .uuid((UUID) rs.getObject("uuid"))
                .area(rs.getString("area"))
                .country(rs.getString("country"))
                .city(rs.getString("city"))
                .street(rs.getString("street"))
                .number(rs.getString("number"))
                .createDate(rs.getTimestamp("create_date").toLocalDateTime())
                .build();

        return house;
    }

}