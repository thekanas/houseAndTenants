package by.stolybko.service.impl;

import by.stolybko.database.dto.HouseRequestDTO;
import by.stolybko.database.dto.HouseResponseDTO;
import by.stolybko.database.entity.HouseEntity;
import by.stolybko.database.entity.PersonEntity;
import by.stolybko.database.repository.HouseRepository;
import by.stolybko.database.repository.PersonRepository;
import by.stolybko.exeption.EntityNotFoundException;
import by.stolybko.service.HouseService;
import by.stolybko.service.mapper.HouseMapper;
import by.stolybko.service.mapper.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;
    private final PersonRepository personRepository;
    private final HouseMapper houseMapper;
    private final PersonMapper personMapper;

    @Override
    public HouseResponseDTO getByUuid(UUID uuid) {
        HouseEntity houseEntity = houseRepository.findByUuid(uuid)
                .orElseThrow(() -> EntityNotFoundException.of(HouseEntity.class, uuid));

        return houseMapper.toHouseResponseDTO(houseEntity);
    }

    @Override
    public List<HouseResponseDTO> getAll() {
        List<HouseResponseDTO> houses = new ArrayList<>();
        for (HouseEntity house : houseRepository.findAll()) {
            houses.add(houseMapper.toHouseResponseDTO(house));
        }
        return houses;
    }

    @Override
    @Transactional
    public void create(HouseRequestDTO dto) {
        HouseEntity savedHouse = houseMapper.toHouseEntity(dto);

        setOwners(savedHouse, dto);

        houseRepository.save(savedHouse);
    }

    @Override
    @Transactional
    public void update(UUID uuid, HouseRequestDTO dto) {
        HouseEntity house = houseRepository.findByUuid(uuid)
                .orElseThrow(() -> EntityNotFoundException.of(HouseEntity.class, uuid));
        HouseEntity updatedHouse = houseMapper.update(house, dto);

        setOwners(updatedHouse, dto);

        houseRepository.update(updatedHouse);
    }

    @Override
    @Transactional
    public void patch(UUID uuid, HouseRequestDTO dto) {
        HouseEntity updatedHouse = houseRepository.findByUuid(uuid)
                .orElseThrow(() -> EntityNotFoundException.of(HouseEntity.class, uuid));
        updatedHouse = houseMapper.merge(updatedHouse, dto);

        setOwners(updatedHouse, dto);

        houseRepository.update(updatedHouse);
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {
        houseRepository.delete(uuid);
    }

    @Override
    public List<HouseResponseDTO> getOwnershipByPersonUuid(UUID uuid) {
        PersonEntity owner = personRepository.findByUuid(uuid)
                .orElseThrow(() -> EntityNotFoundException.of(PersonEntity.class, uuid));
        List<HouseResponseDTO> ownership = new ArrayList<>();
        for (HouseEntity house : owner.getOwnership()) {
            ownership.add(houseMapper.toHouseResponseDTO(house));
        }
        return ownership;
    }

    private void setOwners(HouseEntity house, HouseRequestDTO dto) {
        List<UUID> ownersUuid = dto.getOwnersUuid();
        if(ownersUuid != null && !ownersUuid.isEmpty()) {

            for(PersonEntity person : house.getOwners()) {
                person.getOwnership().remove(house);
            }
            house.getOwners().clear();

            for (UUID ownerUuid : ownersUuid) {
                PersonEntity owner = personRepository.findByUuid(ownerUuid)
                        .orElseThrow(() -> EntityNotFoundException.of(PersonEntity.class, ownerUuid));
                house.addOwner(owner);
            }
        }
    }

}
