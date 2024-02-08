package by.stolybko.service.impl;

import by.stolybko.database.dto.request.PersonRequestDTO;
import by.stolybko.database.dto.response.PersonResponseDTO;
import by.stolybko.database.entity.HouseEntity;
import by.stolybko.database.entity.PersonEntity;
import by.stolybko.database.repository.HouseRepository;
import by.stolybko.database.repository.PersonRepository;
import by.stolybko.handler.EntityNotCreatedException;
import by.stolybko.handler.EntityNotFoundException;
import by.stolybko.service.PersonService;
import by.stolybko.service.mapper.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final HouseRepository houseRepository;
    private final PersonMapper personMapper;

    @Override
    public PersonResponseDTO getByUuid(UUID uuid) {
        PersonEntity personEntity = personRepository.findPersonEntityByUuid(uuid)
                .orElseThrow(() -> EntityNotFoundException.of(PersonResponseDTO.class, uuid));

        return personMapper.toPersonResponseDTO(personEntity);
    }

    @Override
    public Page<PersonResponseDTO> getAll(Pageable pageable) {

        return personRepository.findAll(pageable)
                .map(personMapper::toPersonResponseDTO);
    }

    @Override
    @Transactional
    public PersonResponseDTO create(PersonRequestDTO dto) {
        PersonEntity savedPerson = personMapper.toPersonEntity(dto);
        HouseEntity house = houseRepository.findHouseEntityByUuid(dto.houseUuid())
                .orElseThrow(() -> EntityNotFoundException.of(HouseEntity.class, dto.houseUuid()));
        savedPerson.setHouse(house);

        try {
            savedPerson = personRepository.save(savedPerson);
        } catch (Exception e) {
            throw new EntityNotCreatedException(e.getCause().getCause().getMessage());
        }
        return personMapper.toPersonResponseDTO(savedPerson);
    }

    @Override
    @Transactional
    public PersonResponseDTO update(UUID uuid, PersonRequestDTO dto) {
        PersonEntity updatedPerson = personRepository.findPersonEntityByUuid(uuid)
                .orElseThrow(() -> EntityNotFoundException.of(PersonEntity.class, uuid));
        HouseEntity house = houseRepository.findHouseEntityByUuid(dto.houseUuid())
                .orElseThrow(() -> EntityNotFoundException.of(HouseEntity.class, dto.houseUuid()));
        updatedPerson = personMapper.update(updatedPerson, dto);
        updatedPerson.setHouse(house);

        updatedPerson = personRepository.save(updatedPerson);
        return personMapper.toPersonResponseDTO(updatedPerson);
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {
        personRepository.deletePersonEntityByUuid(uuid);
    }

    @Override
    public List<PersonResponseDTO> getTenantsByHouseUuid(UUID uuid) {
        HouseEntity house = houseRepository.findHouseEntityByUuid(uuid)
                .orElseThrow(() -> EntityNotFoundException.of(HouseEntity.class, uuid));
        List<PersonEntity> tenants = personRepository.findPersonEntitiesByHouseUuid(house.getUuid());

        return tenants.stream().map(personMapper::toPersonResponseDTO).toList();
    }


}
