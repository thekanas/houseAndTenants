package by.stolybko.service.impl;

import by.stolybko.database.dto.HouseRequestDTO;
import by.stolybko.database.dto.HouseResponseDTO;
import by.stolybko.database.dto.PersonResponseDTO;
import by.stolybko.database.entity.HouseEntity;
import by.stolybko.database.entity.PersonEntity;
import by.stolybko.database.repository.HouseRepository;
import by.stolybko.database.repository.PersonRepository;
import by.stolybko.service.HouseService;
import by.stolybko.service.mapper.HouseMapper;
import by.stolybko.service.mapper.PersonMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;
    private final PersonRepository personRepository;
    private final HouseMapper houseMapper;
    private final PersonMapper personMapper;

    @Override
    public HouseResponseDTO getByUuid(UUID uuid) {
        HouseEntity houseEntity = houseRepository.findByUuid(uuid)
                .orElseThrow();

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
    //@Transactional
    public void create(HouseRequestDTO dto) {
        HouseEntity savedHouse = houseMapper.toHouseEntity(dto);

        setOwners(savedHouse, dto);

        houseRepository.save(savedHouse);
    }

    @Override
    //@Transactional
    public void update(UUID uuid, HouseRequestDTO dto) {
        HouseEntity updatedHouse = houseRepository.findByUuid(uuid).orElseThrow();
        updatedHouse.setArea(dto.getArea());
        updatedHouse.setCountry(dto.getCountry());
        updatedHouse.setCity(dto.getCity());
        updatedHouse.setStreet(dto.getStreet());
        updatedHouse.setNumber(dto.getNumber());

        setOwners(updatedHouse, dto);

        houseRepository.update(updatedHouse);
    }

    @Override
    //@Transactional
    public void delete(UUID uuid) {
        houseRepository.delete(uuid);
    }

    public List<PersonResponseDTO> getTenantsByHouseUuid(UUID uuid) {
        List<PersonResponseDTO> tenants = new ArrayList<>();
        HouseEntity house = houseRepository.findByUuid(uuid).orElseThrow();
        for (PersonEntity tenant : house.getTenants()) {
            tenants.add(personMapper.toPersonResponseDTO(tenant));
        }
        return tenants;
    }

    private void setOwners(HouseEntity house, HouseRequestDTO dto) {
        List<UUID> ownersUuid = dto.getOwnersUuid();
        if(ownersUuid != null && !ownersUuid.isEmpty()) {
            house.setOwners(new ArrayList<>());
            for (UUID ownerUuid : ownersUuid) {
                PersonEntity owner = personRepository.findByUuid(ownerUuid).orElseThrow();
                house.addOwner(owner);
            }
        }
    }

}
