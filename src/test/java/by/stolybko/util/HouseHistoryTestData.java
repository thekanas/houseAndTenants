package by.stolybko.util;

import by.stolybko.database.dto.response.HouseHistoryResponseDTO;
import by.stolybko.database.dto.response.PersonHistoryResponseDTO;
import by.stolybko.database.entity.HouseEntity;
import by.stolybko.database.entity.HouseHistory;
import by.stolybko.database.entity.PersonEntity;
import by.stolybko.database.entity.enam.Type;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;

@Data
@Builder(setterPrefix = "with", toBuilder = true)
@Accessors(chain = true)
public class HouseHistoryTestData {

    @Builder.Default
    private HouseEntity house = HouseTestData.builder().build().buildHouseEntity();


    @Builder.Default
    private PersonEntity person = PersonTestData.builder().build().buildPersonEntity();


    @Builder.Default
    private LocalDateTime exitDate = LocalDateTime.MIN;


    @Builder.Default
    private Type type = Type.TENANT;

    public HouseHistory buildHouseHistory() {
        return new HouseHistory(1L, house, person, exitDate, type);
    }

    public PersonHistoryResponseDTO buildPersonHistoryResponseDTO() {
        return new PersonHistoryResponseDTO(person.getUuid(),
                person.getName(),
                person.getSurname(),
                person.getSex(),
                person.getPassport(),
                person.getCreateDate(),
                person.getUpdateDate(),
                exitDate);
    }

    public HouseHistoryResponseDTO buildHouseHistoryResponseDTO() {
        return new HouseHistoryResponseDTO(
                house.getUuid(),
                house.getArea(),
                house.getCountry(),
                house.getCity(),
                house.getStreet(),
                house.getNumber(),
                house.getCreateDate(),
                exitDate);
    }

}
