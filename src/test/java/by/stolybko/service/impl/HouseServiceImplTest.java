package by.stolybko.service.impl;

import by.stolybko.database.dto.request.HouseRequestDTO;
import by.stolybko.database.dto.response.HouseResponseDTO;
import by.stolybko.database.entity.HouseEntity;
import by.stolybko.database.entity.PersonEntity;
import by.stolybko.database.repository.HouseRepository;
import by.stolybko.database.repository.PersonRepository;
import by.stolybko.service.mapper.HouseMapper;
import by.stolybko.util.HouseTestData;
import by.stolybko.util.PersonTestData;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class HouseServiceImplTest {

    @Mock
    private HouseRepository houseRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private HouseMapper houseMapper;

    @Captor
    private ArgumentCaptor<HouseEntity> houseCaptor;

    @InjectMocks
    private HouseServiceImpl houseService;

    @Test
    void getByUuidTest() {

        // given
        UUID uuid = HouseTestData.builder()
                .build().getHouseUuid();
        HouseEntity houseEntity = HouseTestData.builder()
                .build().buildHouseEntity();
        HouseResponseDTO expected = HouseTestData.builder()
                .build().buildHouseResponseDTO();

        when(houseMapper.toHouseResponseDTO(houseEntity))
                .thenReturn(expected);
        when(houseRepository.findHouseEntityByUuid(uuid))
                .thenReturn(Optional.of(houseEntity));

        // when
        HouseResponseDTO actual = houseService.getByUuid(uuid);

        // then
        assertThat(actual).isEqualTo(expected);
        
    }

    @Test
    void getAllTest() {
        
        // given
        UUID uuid2 = UUID.fromString("c134c54f-9d37-4763-ab7e-db03caf9dad6");
        HouseEntity houseEntity1 = HouseTestData.builder()
                .build().buildHouseEntity();
        HouseEntity houseEntity2 = HouseTestData.builder()
                .withHouseUuid(uuid2)
                .build().buildHouseEntity();
        HouseResponseDTO houseResponseDTO1 = HouseTestData.builder()
                .build().buildHouseResponseDTO();
        HouseResponseDTO houseResponseDTO2 = HouseTestData.builder()
                .withHouseUuid(uuid2)
                .build().buildHouseResponseDTO();

        Page<HouseEntity> houseEntities = new PageImpl<>(List.of(houseEntity1, houseEntity2), Pageable.ofSize(20), 20);
        Page<HouseResponseDTO> expected = new PageImpl<>(List.of(houseResponseDTO1, houseResponseDTO2), Pageable.ofSize(20), 20);

        when(houseMapper.toHouseResponseDTO(houseEntity1))
                .thenReturn(houseResponseDTO1);
        when(houseMapper.toHouseResponseDTO(houseEntity2))
                .thenReturn(houseResponseDTO2);
        when(houseRepository.findAll(Pageable.ofSize(20)))
                .thenReturn(houseEntities);

        // when
        Page<HouseResponseDTO> actual = houseService.getAll(Pageable.ofSize(20));

        // then
        assertIterableEquals(expected, actual);
    }

    @Test
    void createShouldSetOwnersIfPresent_whenInvoke() {

        // given
        UUID ownerUuid1 = UUID.fromString("c114c54f-9d37-4763-ab7e-db03caf9dad6");
        UUID ownerUuid2 = UUID.fromString("c214c54f-9d37-4763-ab7e-db03caf9dad6");

        List<UUID> ownersUuid = List.of(ownerUuid1, ownerUuid2);

        HouseRequestDTO houseRequestDTO = HouseTestData.builder()
                .build()
                .setOwnersUuid(ownersUuid)
                .buildHouseRequestDTO();
        HouseEntity houseEntity = HouseTestData.builder()
                .build().buildHouseEntity();

        PersonEntity owner1 = PersonTestData.builder()
                .withPersonUuid(ownerUuid1)
                .build().buildPersonEntity();
        PersonEntity owner2 = PersonTestData.builder()
                .withPersonUuid(ownerUuid2)
                .build().buildPersonEntity();
        List<PersonEntity> owners = List.of(owner1, owner2);

        when(houseMapper.toHouseEntity(houseRequestDTO))
                .thenReturn(houseEntity);
        when(personRepository.findPersonEntityByUuid(ownerUuid1))
                .thenReturn(Optional.of(owner1));
        when(personRepository.findPersonEntityByUuid(ownerUuid2))
                .thenReturn(Optional.of(owner2));

        // when
        houseService.create(houseRequestDTO);

        // then
        verify(houseRepository).save(houseCaptor.capture());
        assertThat(houseCaptor.getValue())
                .hasFieldOrPropertyWithValue(HouseEntity.Fields.owners, owners);

    }

    @Test
    void updateTest() {

        // given
        UUID houseUuid = HouseTestData.builder()
                .build().getHouseUuid();

        UUID ownerUuid1 = UUID.fromString("c114c54f-9d37-4763-ab7e-db03caf9dad6");
        UUID ownerUuid2 = UUID.fromString("c214c54f-9d37-4763-ab7e-db03caf9dad6");

        List<UUID> ownersUuid = new ArrayList<>();
        ownersUuid.add(ownerUuid1);
        ownersUuid.add(ownerUuid2);

        HouseRequestDTO houseRequestDTO = HouseTestData.builder()
                .withCity("Gomel")
                .withNumber("99")
                .withOwnersUuid(ownersUuid)
                .build().buildHouseRequestDTO();
        HouseEntity houseOld = HouseTestData.builder()
                .build().buildHouseEntity();


        PersonEntity owner1 = PersonTestData.builder()
                .withPersonUuid(ownerUuid1)
                .build().buildPersonEntity();
        PersonEntity owner2 = PersonTestData.builder()
                .withPersonUuid(ownerUuid2)
                .build().buildPersonEntity();

        List<PersonEntity> owners = new ArrayList<>();
        owners.add(owner1);
        owners.add(owner2);

        HouseEntity houseUpdate = HouseTestData.builder()
                .withCity("Gomel")
                .withNumber("99")
                .withOwners(owners)
                .build().buildHouseEntity();

        when(houseRepository.findHouseEntityByUuid(houseUuid))
                .thenReturn(Optional.of(houseOld));
        when(houseMapper.update(any(), any()))
                .thenReturn(houseUpdate);
        when(personRepository.findPersonEntityByUuid(ownerUuid1))
                .thenReturn(Optional.of(owner1));
        when(personRepository.findPersonEntityByUuid(ownerUuid2))
                .thenReturn(Optional.of(owner2));

        // when
        houseService.update(houseUuid, houseRequestDTO);

        // then
        verify(houseRepository).save(houseCaptor.capture());
        assertThat(houseCaptor.getValue())
                .isEqualTo(houseUpdate)
                .hasFieldOrPropertyWithValue(HouseEntity.Fields.owners, owners);
    }

    @Test
    void deleteTest() {

        // given
        UUID uuid = HouseTestData.builder().build().getHouseUuid();

        // when
        houseService.delete(uuid);

        // then
        verify(houseRepository).deleteHouseEntityByUuid(uuid);
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

        when(personRepository.findPersonEntityByUuid(personUuid))
                .thenReturn(Optional.of(personEntity));
        when(houseRepository.findAllByOwners_Uuid(personUuid))
                .thenReturn(ownership);
        when(houseMapper.toHouseResponseDTO(houseEntity1))
                .thenReturn(houseResponseDTO1);
        when(houseMapper.toHouseResponseDTO(houseEntity2))
                .thenReturn(houseResponseDTO2);

        // when
        List<HouseResponseDTO> actual = houseService.getOwnershipByPersonUuid(personUuid);

        // then
        assertIterableEquals(expected, actual);
    }
}