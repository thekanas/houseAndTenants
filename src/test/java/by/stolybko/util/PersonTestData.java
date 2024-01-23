package by.stolybko.util;

import by.stolybko.database.dto.PersonRequestDTO;
import by.stolybko.database.dto.PersonResponseDTO;
import by.stolybko.database.entity.HouseEntity;
import by.stolybko.database.entity.Passport;
import by.stolybko.database.entity.PersonEntity;
import by.stolybko.database.entity.enam.Sex;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder(setterPrefix = "with", toBuilder = true)
@Accessors(chain = true)
public class PersonTestData {

    @Builder.Default
    private UUID personUuid = UUID.fromString("25486810-43dd-41e8-ab60-98aa2d200acb");

    @Builder.Default
    private String name = "TestName";

    @Builder.Default
    private String surname = "TestSurname";

    @Builder.Default
    private Sex sex = Sex.MALE;

    @Builder.Default
    private Passport passport = new Passport("TT", "54321");

    @Builder.Default
    private HouseEntity house = HouseTestData.builder().build().buildHouseEntity();

    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.of(2023, 1, 1, 1, 10, 30);

    @Builder.Default
    private LocalDateTime updateDate = LocalDateTime.of(2023, 1, 1, 1, 10, 30);

    @Builder.Default
    private List<HouseEntity> ownership = new ArrayList<>();

    public PersonEntity buildPersonEntity() {
        return new PersonEntity(1L, personUuid, name, surname, sex, passport, house, createDate, updateDate, ownership);
    }

    public PersonRequestDTO buildPersonRequestDTO() {
        return new PersonRequestDTO(name, surname, sex, passport, house.getUuid());
    }

    public PersonResponseDTO buildPersonResponseDTO() {
        return new PersonResponseDTO(personUuid, name, surname, sex, passport, createDate, updateDate);
    }
}
