package by.stolybko.service.mapper;

import by.stolybko.database.dto.request.PersonRequestDTO;
import by.stolybko.database.dto.response.PersonHistoryResponseDTO;
import by.stolybko.database.dto.response.PersonResponseDTO;
import by.stolybko.database.entity.HouseHistory;
import by.stolybko.database.entity.PersonEntity;
import by.stolybko.util.HouseHistoryTestData;
import by.stolybko.util.PersonTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PersonMapperTest {

    @Autowired
    private PersonMapper personMapper;

    @Test
    void toPersonEntity() {
        // given
        PersonEntity expected = PersonTestData.builder()
                .withId(null)
                .withPersonUuid(null)
                .withHouse(null)
                .withCreateDate(null)
                .withUpdateDate(null)
                .build().buildPersonEntity();
        PersonRequestDTO personRequestDTO = PersonTestData.builder()
                .build().buildPersonRequestDTO();

        // when
        PersonEntity actual = personMapper.toPersonEntity(personRequestDTO);

        // then
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void toPersonResponseDTO() {
        // given
        PersonEntity person = PersonTestData.builder()
                .build().buildPersonEntity();
        PersonResponseDTO expected = PersonTestData.builder()
                .build().buildPersonResponseDTO();

        // when
        PersonResponseDTO actual = personMapper.toPersonResponseDTO(person);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void toPersonHistoryResponseDTO() {
        // given
        HouseHistory houseHistory = HouseHistoryTestData.builder()
                .build().buildHouseHistory();
        PersonHistoryResponseDTO expected = HouseHistoryTestData.builder()
                .build().buildPersonHistoryResponseDTO();
        // when
        PersonHistoryResponseDTO actual = personMapper.toPersonHistoryResponseDTO(houseHistory);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void update() {
        // given
        PersonEntity person = PersonTestData.builder()
                .build().buildPersonEntity();
        PersonEntity expected = PersonTestData.builder()
                .withName("TestNameNew")
                .withSurname("TestSurnameNew")
                .build().buildPersonEntity();
        PersonRequestDTO personRequestDTO = PersonTestData.builder()
                .withName("TestNameNew")
                .withSurname("TestSurnameNew")
                .build().buildPersonRequestDTO();

        // when
        PersonEntity actual = personMapper.update(person, personRequestDTO);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}