package by.stolybko.service.impl;

import by.stolybko.database.dto.response.HouseHistoryResponseDTO;
import by.stolybko.database.dto.response.PersonHistoryResponseDTO;
import by.stolybko.database.dto.response.PersonResponseDTO;
import by.stolybko.database.entity.HouseEntity;
import by.stolybko.database.entity.HouseHistory;
import by.stolybko.database.entity.PersonEntity;
import by.stolybko.database.entity.enam.Type;
import by.stolybko.database.repository.HouseHistoryRepository;
import by.stolybko.database.repository.HouseRepository;
import by.stolybko.database.repository.PersonRepository;
import by.stolybko.exeption.EntityNotFoundException;
import by.stolybko.service.HouseHistoryService;
import by.stolybko.service.mapper.HouseMapper;
import by.stolybko.service.mapper.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HouseHistoryServiceImpl implements HouseHistoryService {

    private final HouseHistoryRepository houseHistoryRepository;
    private final HouseRepository houseRepository;
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final HouseMapper houseMapper;

    @Override
    public List<PersonHistoryResponseDTO> getPersonTenantHistoryByHouseUuid(UUID houseUuid) {
        HouseEntity house = houseRepository.findHouseEntityByUuid(houseUuid)
                .orElseThrow(() -> EntityNotFoundException.of(HouseEntity.class, houseUuid));

        List<HouseHistory> tenants = houseHistoryRepository.findPersonsByHouse(house);

        return tenants.stream()
                .filter(h -> h.getType() == Type.TENANT)
                .map(personMapper::toPersonHistoryResponseDTO)
                .toList();

    }

    @Override
    public List<PersonHistoryResponseDTO> getPersonOwnerHistoryByHouseUuid(UUID houseUuid) {
        HouseEntity house = houseRepository.findHouseEntityByUuid(houseUuid)
                .orElseThrow(() -> EntityNotFoundException.of(HouseEntity.class, houseUuid));

        List<HouseHistory> owners = houseHistoryRepository.findPersonsByHouse(house);

        return owners.stream()
                .filter(h -> h.getType() == Type.OWNER)
                .map(personMapper::toPersonHistoryResponseDTO)
                .toList();

    }

    @Override
    public List<HouseHistoryResponseDTO> getHousesHistoryByPersonTenantUuid(UUID tenantUuid) {
        PersonEntity personEntity = personRepository.findPersonEntityByUuid(tenantUuid)
                .orElseThrow(() -> EntityNotFoundException.of(PersonResponseDTO.class, tenantUuid));

        List<HouseHistory> houses = houseHistoryRepository.findHousesByPerson(personEntity);

        return houses.stream()
                .filter(h -> h.getType() == Type.TENANT)
                .map(houseMapper::toHouseHistoryResponseDTO)
                .toList();

    }

    @Override
    public List<HouseHistoryResponseDTO> getHousesHistoryByPersonOwnerUuid(UUID tenantUuid) {
        PersonEntity personEntity = personRepository.findPersonEntityByUuid(tenantUuid)
                .orElseThrow(() -> EntityNotFoundException.of(PersonResponseDTO.class, tenantUuid));

        List<HouseHistory> houses = houseHistoryRepository.findHousesByPerson(personEntity);

        return houses.stream()
                .filter(h -> h.getType() == Type.OWNER)
                .map(houseMapper::toHouseHistoryResponseDTO)
                .toList();

    }

}
