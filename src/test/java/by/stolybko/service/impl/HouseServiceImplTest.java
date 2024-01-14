package by.stolybko.service.impl;

import by.stolybko.database.dto.HouseRequestDTO;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class HouseServiceImplTest {
    // given
    // when
    // then
    @Mock
    private HouseRepository houseRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private HouseMapper houseMapper;

    @Captor
    private ArgumentCaptor<HouseEntity> houseCaptor;

    @Mock
    private PersonMapper personMapper;

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
        when(houseRepository.findByUuid(uuid))
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

        List<HouseEntity> houseEntities = List.of(houseEntity1, houseEntity2);
        List<HouseResponseDTO> expected = List.of(houseResponseDTO1, houseResponseDTO2);

        when(houseMapper.toHouseResponseDTO(houseEntity1))
                .thenReturn(houseResponseDTO1);
        when(houseMapper.toHouseResponseDTO(houseEntity2))
                .thenReturn(houseResponseDTO2);
        when(houseRepository.findAll())
                .thenReturn(houseEntities);

        // when
        List<HouseResponseDTO> actual = houseService.getAll();

        // then
        assertIterableEquals(expected, actual);
    }

    @Test
    void createShouldSetOwnersIfPresent_whenInvoke() {

        // given
        HouseRequestDTO houseRequestDTO = HouseTestData.builder()
                .build().buildHouseRequestDTO();
        HouseEntity houseEntity = HouseTestData.builder()
                .build().buildHouseEntity();

        UUID ownerUuid1 = UUID.fromString("c114c54f-9d37-4763-ab7e-db03caf9dad6");
        UUID ownerUuid2 = UUID.fromString("c214c54f-9d37-4763-ab7e-db03caf9dad6");

        List<UUID> ownersUuid = List.of(ownerUuid1, ownerUuid2);
        houseRequestDTO.setOwnersUuid(ownersUuid);

        PersonEntity owner1 = PersonTestData.builder()
                .withPersonUuid(ownerUuid1)
                .build().buildPersonEntity();
        PersonEntity owner2 = PersonTestData.builder()
                .withPersonUuid(ownerUuid2)
                .build().buildPersonEntity();
        List<PersonEntity> owners = List.of(owner1, owner2);

        when(houseMapper.toHouseEntity(houseRequestDTO))
                .thenReturn(houseEntity);
        when(personRepository.findByUuid(ownerUuid1))
                .thenReturn(Optional.of(owner1));
        when(personRepository.findByUuid(ownerUuid2))
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
        HouseRequestDTO houseRequestDTO = HouseTestData.builder()
                .withCity("Gomel")
                .withNumber("99")
                .build().buildHouseRequestDTO();
        HouseEntity houseOld = HouseTestData.builder()
                .build().buildHouseEntity();

        UUID ownerUuid1 = UUID.fromString("c114c54f-9d37-4763-ab7e-db03caf9dad6");
        UUID ownerUuid2 = UUID.fromString("c214c54f-9d37-4763-ab7e-db03caf9dad6");

        List<UUID> ownersUuid = List.of(ownerUuid1, ownerUuid2);
        houseRequestDTO.setOwnersUuid(ownersUuid);

        PersonEntity owner1 = PersonTestData.builder()
                .withPersonUuid(ownerUuid1)
                .build().buildPersonEntity();
        PersonEntity owner2 = PersonTestData.builder()
                .withPersonUuid(ownerUuid2)
                .build().buildPersonEntity();

        List<PersonEntity> owners = List.of(owner1, owner2);

        HouseEntity houseUpdate = HouseTestData.builder()
                .withCity("Gomel")
                .withNumber("99")
                .withOwners(owners)
                .build().buildHouseEntity();

        when(houseRepository.findByUuid(houseUuid))
                .thenReturn(Optional.of(houseOld));
        when(personRepository.findByUuid(ownerUuid1))
                .thenReturn(Optional.of(owner1));
        when(personRepository.findByUuid(ownerUuid2))
                .thenReturn(Optional.of(owner2));

        // when
        houseService.update(houseUuid, houseRequestDTO);

        // then
        verify(houseRepository).update(houseCaptor.capture());
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
        verify(houseRepository).delete(uuid);
    }

    @Test
    void getTenantsByHouseUuidTest() {

        // given
        UUID uuid = HouseTestData.builder().build().getHouseUuid();
        UUID tenantUuid1 = UUID.fromString("c114c54f-9d37-4763-ab7e-db03caf9dad6");
        UUID tenantUuid2 = UUID.fromString("c214c54f-9d37-4763-ab7e-db03caf9dad6");


        PersonEntity tenant1 = PersonTestData.builder()
                .withPersonUuid(tenantUuid1)
                .build().buildPersonEntity();
        PersonEntity tenant2 = PersonTestData.builder()
                .withPersonUuid(tenantUuid2)
                .build().buildPersonEntity();
        PersonResponseDTO personResponseDTO1 = PersonTestData.builder()
                .withPersonUuid(tenantUuid1)
                .build().buildPersonResponseDTO();
        PersonResponseDTO personResponseDTO2 = PersonTestData.builder()
                .withPersonUuid(tenantUuid2)
                .build().buildPersonResponseDTO();

        List<PersonEntity> tenants = List.of(tenant1, tenant2);
        List<PersonResponseDTO> expected = List.of(personResponseDTO1, personResponseDTO2);

        HouseEntity house = HouseTestData.builder()
                .withTenants(tenants)
                .build().buildHouseEntity();

        when(houseRepository.findByUuid(uuid))
                .thenReturn(Optional.of(house));
        when(personMapper.toPersonResponseDTO(tenant1))
                .thenReturn(personResponseDTO1);
        when(personMapper.toPersonResponseDTO(tenant2))
                .thenReturn(personResponseDTO2);

        // when
        List<PersonResponseDTO> actual = houseService.getTenantsByHouseUuid(uuid);

        // then
        assertIterableEquals(expected, actual);
    }
}