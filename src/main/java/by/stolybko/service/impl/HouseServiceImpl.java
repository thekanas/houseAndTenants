package by.stolybko.service.impl;

import by.stolybko.database.dto.request.HouseRequestDTO;
import by.stolybko.database.dto.response.HouseResponseDTO;
import by.stolybko.database.entity.HouseEntity;
import by.stolybko.database.entity.PersonEntity;
import by.stolybko.database.repository.HouseRepository;
import by.stolybko.database.repository.PersonRepository;
import by.stolybko.handler.EntityNotFoundException;
import by.stolybko.service.HouseService;
import by.stolybko.service.mapper.HouseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;
    private final PersonRepository personRepository;
    private final HouseMapper houseMapper;

    @Override
    public HouseResponseDTO getByUuid(UUID uuid) {
        HouseEntity houseEntity = houseRepository.findHouseEntityByUuid(uuid)
                .orElseThrow(() -> EntityNotFoundException.of(HouseEntity.class, uuid));

        return houseMapper.toHouseResponseDTO(houseEntity);
    }

    @Override
    public Page<HouseResponseDTO> getAll(Pageable pageable) {

        return houseRepository.findAll(pageable)
                .map(houseMapper::toHouseResponseDTO);
    }

    @Override
    @Transactional
    public HouseResponseDTO create(HouseRequestDTO dto) {
        HouseEntity savedHouse = houseMapper.toHouseEntity(dto);

        setOwners(savedHouse, dto);

        savedHouse = houseRepository.save(savedHouse);
        return houseMapper.toHouseResponseDTO(savedHouse);
    }

    @Override
    @Transactional
    public HouseResponseDTO update(UUID uuid, HouseRequestDTO dto) {
        HouseEntity house = houseRepository.findHouseEntityByUuid(uuid)
                .orElseThrow(() -> EntityNotFoundException.of(HouseEntity.class, uuid));
        HouseEntity updatedHouse = houseMapper.update(house, dto);

        setOwners(updatedHouse, dto);

        updatedHouse = houseRepository.save(updatedHouse);
        return houseMapper.toHouseResponseDTO(updatedHouse);
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {
        houseRepository.deleteHouseEntityByUuid(uuid);
    }

    @Override
    public List<HouseResponseDTO> getOwnershipByPersonUuid(UUID uuid) {
        PersonEntity owner = personRepository.findPersonEntityByUuid(uuid)
                .orElseThrow(() -> EntityNotFoundException.of(PersonEntity.class, uuid));
        List<HouseEntity> houseEntities = houseRepository.findAllByOwners_Uuid(owner.getUuid());

        return houseEntities.stream().map(houseMapper::toHouseResponseDTO).toList();
    }

    private void setOwners(HouseEntity house, HouseRequestDTO dto) {
        List<UUID> ownersUuid = dto.ownersUuid();
        if(ownersUuid != null && !ownersUuid.isEmpty()) {

            for(PersonEntity person : house.getOwners()) {
                person.getOwnership().remove(house);
            }
            house.getOwners().clear();

            for (UUID ownerUuid : ownersUuid) {
                PersonEntity owner = personRepository.findPersonEntityByUuid(ownerUuid)
                        .orElseThrow(() -> EntityNotFoundException.of(PersonEntity.class, ownerUuid));
                house.addOwner(owner);
            }
        }
    }

}
