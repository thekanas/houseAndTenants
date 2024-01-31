package by.stolybko.service.impl;

import by.stolybko.database.dto.response.HouseHistoryResponseDTO;
import by.stolybko.database.dto.response.PersonHistoryResponseDTO;
import by.stolybko.database.entity.HouseEntity;
import by.stolybko.database.entity.HouseHistory;
import by.stolybko.database.entity.PersonEntity;
import by.stolybko.database.entity.enam.Type;
import by.stolybko.database.repository.HouseHistoryRepository;
import by.stolybko.database.repository.HouseRepository;
import by.stolybko.database.repository.PersonRepository;
import by.stolybko.service.mapper.HouseMapper;
import by.stolybko.service.mapper.PersonMapper;
import by.stolybko.util.HouseHistoryTestData;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
class HouseHistoryServiceImplTest {

    @MockBean
    private HouseHistoryRepository houseHistoryRepository;

    @MockBean
    private HouseRepository houseRepository;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private PersonMapper personMapper;

    @MockBean
    private HouseMapper houseMapper;

    @Captor
    private ArgumentCaptor<HouseHistory> houseCaptor;

    @Autowired
    private HouseHistoryServiceImpl houseHistoryService;



    @Test
    void getPersonTenantHistoryByHouseUuid() {

        // given
        HouseHistory houseHistory = HouseHistoryTestData.builder()
                .build().buildHouseHistory();
        HouseEntity house = houseHistory.getHouse();
        PersonHistoryResponseDTO personHistoryResponseDTO = HouseHistoryTestData.builder()
                .build().buildPersonHistoryResponseDTO();
        List<PersonHistoryResponseDTO> expected = List.of(personHistoryResponseDTO);

        when(houseRepository.findHouseEntityByUuid(house.getUuid()))
                .thenReturn(Optional.of(house));
        when(houseHistoryRepository.findPersonsByHouseAndType(house, Type.TENANT))
                .thenReturn(List.of(houseHistory));
        when(personMapper.toPersonHistoryResponseDTO(houseHistory))
                .thenReturn(personHistoryResponseDTO);

        // when
        List<PersonHistoryResponseDTO> actual = houseHistoryService.getPersonTenantHistoryByHouseUuid(house.getUuid());

        // then
        assertIterableEquals(expected, actual);
    }

    @Test
    void getPersonOwnerHistoryByHouseUuid() {

        // given
        HouseHistory houseHistory = HouseHistoryTestData.builder()
                .build().buildHouseHistory();
        HouseEntity house = houseHistory.getHouse();
        PersonHistoryResponseDTO personHistoryResponseDTO = HouseHistoryTestData.builder()
                .build().buildPersonHistoryResponseDTO();
        List<PersonHistoryResponseDTO> expected = List.of(personHistoryResponseDTO);

        when(houseRepository.findHouseEntityByUuid(house.getUuid()))
                .thenReturn(Optional.of(house));
        when(houseHistoryRepository.findPersonsByHouseAndType(house, Type.OWNER))
                .thenReturn(List.of(houseHistory));
        when(personMapper.toPersonHistoryResponseDTO(houseHistory))
                .thenReturn(personHistoryResponseDTO);

        // when
        List<PersonHistoryResponseDTO> actual = houseHistoryService.getPersonOwnerHistoryByHouseUuid(house.getUuid());

        // then
        assertIterableEquals(expected, actual);
    }

    @Test
    void getHousesHistoryByPersonTenantUuid() {

        // given
        HouseHistory houseHistory = HouseHistoryTestData.builder()
                .build().buildHouseHistory();
        PersonEntity person = houseHistory.getPerson();
        HouseHistoryResponseDTO houseHistoryResponseDTO = HouseHistoryTestData.builder()
                .build().buildHouseHistoryResponseDTO();
        List<HouseHistoryResponseDTO> expected = List.of(houseHistoryResponseDTO);

        when(personRepository.findPersonEntityByUuid(person.getUuid()))
                .thenReturn(Optional.of(person));
        when(houseHistoryRepository.findHousesByPersonAndType(person, Type.TENANT))
                .thenReturn(List.of(houseHistory));
        when(houseMapper.toHouseHistoryResponseDTO(houseHistory))
                .thenReturn(houseHistoryResponseDTO);

        // when
        List<HouseHistoryResponseDTO> actual = houseHistoryService.getHousesHistoryByPersonTenantUuid(person.getUuid());

        // then
        assertIterableEquals(expected, actual);
    }

    @Test
    void getHousesHistoryByPersonOwnerUuid() {

        // given
        HouseHistory houseHistory = HouseHistoryTestData.builder()
                .build().buildHouseHistory();
        PersonEntity person = houseHistory.getPerson();
        HouseHistoryResponseDTO houseHistoryResponseDTO = HouseHistoryTestData.builder()
                .build().buildHouseHistoryResponseDTO();
        List<HouseHistoryResponseDTO> expected = List.of(houseHistoryResponseDTO);

        when(personRepository.findPersonEntityByUuid(person.getUuid()))
                .thenReturn(Optional.of(person));
        when(houseHistoryRepository.findHousesByPersonAndType(person, Type.OWNER))
                .thenReturn(List.of(houseHistory));
        when(houseMapper.toHouseHistoryResponseDTO(houseHistory))
                .thenReturn(houseHistoryResponseDTO);

        // when
        List<HouseHistoryResponseDTO> actual = houseHistoryService.getHousesHistoryByPersonOwnerUuid(person.getUuid());

        // then
        assertIterableEquals(expected, actual);
    }
}