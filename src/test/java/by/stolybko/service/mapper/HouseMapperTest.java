package by.stolybko.service.mapper;

import by.stolybko.database.dto.request.HouseRequestDTO;
import by.stolybko.database.dto.response.HouseHistoryResponseDTO;
import by.stolybko.database.dto.response.HouseResponseDTO;
import by.stolybko.database.entity.HouseEntity;
import by.stolybko.database.entity.HouseHistory;
import by.stolybko.util.HouseHistoryTestData;
import by.stolybko.util.HouseTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class HouseMapperTest {

    @Autowired
    private HouseMapper houseMapper;

    @Test
    void toHouseEntity() {
        // given
        HouseEntity expected = HouseTestData.builder()
                .withId(null)
                .withHouseUuid(null)
                .withCreateDate(null)
                .build().buildHouseEntity();
        HouseRequestDTO houseRequestDTO = HouseTestData.builder()
                .build().buildHouseRequestDTO();

        // when
        HouseEntity actual = houseMapper.toHouseEntity(houseRequestDTO);

        // then
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void toHouseResponseDTO() {
        // given
        HouseEntity house = HouseTestData.builder()
                .build().buildHouseEntity();
        HouseResponseDTO expected = HouseTestData.builder()
                .build().buildHouseResponseDTO();

        // when
        HouseResponseDTO actual = houseMapper.toHouseResponseDTO(house);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void toHouseHistoryResponseDTO() {
        // given
        HouseHistory houseHistory = HouseHistoryTestData.builder()
                .build().buildHouseHistory();
        HouseHistoryResponseDTO expected = HouseHistoryTestData.builder()
                .build().buildHouseHistoryResponseDTO();

        // when
        HouseHistoryResponseDTO actual = houseMapper.toHouseHistoryResponseDTO(houseHistory);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void update() {
        // given
        HouseEntity house = HouseTestData.builder()
                .build().buildHouseEntity();
        HouseEntity expected = HouseTestData.builder()
                .withStreet("testTest")
                .withCity("testTestTestTest")
                .withNumber("2a")
                .build().buildHouseEntity();
        HouseRequestDTO houseRequestDTO = HouseTestData.builder()
                .withStreet("testTest")
                .withCity("testTestTestTest")
                .withNumber("2a")
                .build().buildHouseRequestDTO();

        // when
        HouseEntity actual = houseMapper.update(house, houseRequestDTO);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}