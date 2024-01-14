package by.stolybko.service.impl;

import by.stolybko.database.dto.HouseResponseDTO;
import by.stolybko.database.dto.PersonRequestDTO;
import by.stolybko.database.dto.PersonResponseDTO;
import by.stolybko.database.entity.HouseEntity;
import by.stolybko.database.entity.PersonEntity;
import by.stolybko.database.repository.HouseRepository;
import by.stolybko.database.repository.PersonRepository;
import by.stolybko.service.mapper.HouseMapper;
import by.stolybko.service.mapper.PersonMapper;
import by.stolybko.util.HouseTestData;
import by.stolybko.util.PersonTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private HouseRepository houseRepository;

    @Mock
    private PersonMapper personMapper;

    @Mock
    private HouseMapper houseMapper;

    @Captor
    private ArgumentCaptor<PersonEntity> personCaptor;

    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    void getByUuidTest() {

        // given
        UUID uuid = PersonTestData.builder()
                .build().getPersonUuid();
        PersonEntity personEntity = PersonTestData.builder()
                .build().buildPersonEntity();
        PersonResponseDTO expected = PersonTestData.builder()
                .build().buildPersonResponseDTO();

        when(personMapper.toPersonResponseDTO(personEntity))
                .thenReturn(expected);
        when(personRepository.findByUuid(uuid))
                .thenReturn(Optional.of(personEntity));

        // when
        PersonResponseDTO actual = personService.getByUuid(uuid);

        // then
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void getAllTest() {

        // given
        UUID uuid2 = UUID.fromString("c114c54f-9d37-4763-ab7e-db03caf9dad6");
        PersonEntity personEntity1 = PersonTestData.builder()
                .build().buildPersonEntity();
        PersonEntity personEntity2 = PersonTestData.builder()
                .withPersonUuid(uuid2)
                .build().buildPersonEntity();
        PersonResponseDTO personResponseDTO1 = PersonTestData.builder()
                .build().buildPersonResponseDTO();
        PersonResponseDTO personResponseDTO2 = PersonTestData.builder()
                .withPersonUuid(uuid2)
                .build().buildPersonResponseDTO();

        List<PersonEntity> personEntities = List.of(personEntity1, personEntity2);
        List<PersonResponseDTO> expected = List.of(personResponseDTO1, personResponseDTO2);

        when(personMapper.toPersonResponseDTO(personEntity1))
                .thenReturn(personResponseDTO1);
        when(personMapper.toPersonResponseDTO(personEntity2))
                .thenReturn(personResponseDTO2);
        when(personRepository.findAll())
                .thenReturn(personEntities);

        // when
        List<PersonResponseDTO> actual = personService.getAll();

        // then
        assertIterableEquals(expected, actual);
    }

    @Test
    void createShouldSetHouse_whenInvoke() {
        // given
        PersonRequestDTO personRequestDTO = PersonTestData.builder()
                .build().buildPersonRequestDTO();
        PersonEntity personEntity = PersonTestData.builder()
                .withPersonUuid(null)
                .withHouse(null)
                .build().buildPersonEntity();
        HouseEntity houseEntity = HouseTestData.builder()
                .build().buildHouseEntity();
        when(personMapper.toPersonEntity(personRequestDTO))
                .thenReturn(personEntity);
        when(houseRepository.findByUuid(personRequestDTO.getHouseUuid()))
                .thenReturn(Optional.of(houseEntity));

        // when
        personService.create(personRequestDTO);

        // then
        verify(personRepository).save(personCaptor.capture());
        assertThat(personCaptor.getValue())
                .hasFieldOrPropertyWithValue(PersonEntity.Fields.uuid, null)
                .hasFieldOrPropertyWithValue(PersonEntity.Fields.house, houseEntity);

    }

    @Test
    void updateTest() {
        // given
        UUID personUuid = PersonTestData.builder()
                .build().getPersonUuid();
        PersonRequestDTO personRequestDTO = PersonTestData.builder()
                .withName("TestNameUpdate")
                .withSurname("TestSurnameUpdate")
                .build().buildPersonRequestDTO();
        PersonEntity personOld = PersonTestData.builder()
                .withHouse(null)
                .build().buildPersonEntity();
        HouseEntity houseEntity = HouseTestData.builder()
                .build().buildHouseEntity();

        PersonEntity personUpdate = PersonTestData.builder()
                .withName("TestNameUpdate")
                .withSurname("TestSurnameUpdate")
                .build().buildPersonEntity();

        when(personRepository.findByUuid(personUuid))
                .thenReturn(Optional.of(personOld));
        when(houseRepository.findByUuid(personRequestDTO.getHouseUuid()))
                .thenReturn(Optional.of(houseEntity));

        // when
        personService.update(personUuid, personRequestDTO);

        // then
        verify(personRepository).update(personCaptor.capture());
        assertThat(personCaptor.getValue())
                .isEqualTo(personUpdate)
                .hasFieldOrPropertyWithValue(PersonEntity.Fields.house, houseEntity);
    }

    @Test
    void deleteTest() {
        // given
        UUID uuid = PersonTestData.builder().build().getPersonUuid();

        // when
        personService.delete(uuid);

        // then
        verify(personRepository).delete(uuid);
    }

    @Test
    void getOwnershipByPersonUuidTest() {

        // given
        HouseEntity houseEntity1 = HouseTestData.builder()
                .withNumber("51")
                .build().buildHouseEntity();
        HouseEntity houseEntity2 = HouseTestData.builder()
                .withNumber("52")
                .build().buildHouseEntity();
        HouseResponseDTO houseResponseDTO1 = HouseTestData.builder()
                .withNumber("51")
                .build().buildHouseResponseDTO();
        HouseResponseDTO houseResponseDTO2 = HouseTestData.builder()
                .withNumber("52")
                .build().buildHouseResponseDTO();

        List<HouseEntity> ownership = List.of(houseEntity1, houseEntity2);
        List<HouseResponseDTO> expected = List.of(houseResponseDTO1, houseResponseDTO2);

        UUID personUuid = PersonTestData.builder()
                .build().getPersonUuid();
        PersonEntity personEntity = PersonTestData.builder()
                .build().buildPersonEntity();
        personEntity.setOwnership(ownership);

        when(personRepository.findByUuid(personUuid))
                .thenReturn(Optional.of(personEntity));
        when(houseMapper.toHouseResponseDTO(houseEntity1))
                .thenReturn(houseResponseDTO1);
        when(houseMapper.toHouseResponseDTO(houseEntity2))
                .thenReturn(houseResponseDTO2);

        // when
        List<HouseResponseDTO> actual = personService.getOwnershipByPersonUuid(personUuid);

        // then
        assertIterableEquals(expected, actual);
    }

}