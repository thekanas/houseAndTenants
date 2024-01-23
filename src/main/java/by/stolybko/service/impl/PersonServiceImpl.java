package by.stolybko.service.impl;

import by.stolybko.database.dto.PersonRequestDTO;
import by.stolybko.database.dto.PersonResponseDTO;
import by.stolybko.database.entity.HouseEntity;
import by.stolybko.database.entity.PersonEntity;
import by.stolybko.database.repository.HouseRepository;
import by.stolybko.database.repository.PersonRepository;
import by.stolybko.exeption.EntityNotFoundException;
import by.stolybko.service.PersonService;
import by.stolybko.service.mapper.HouseMapper;
import by.stolybko.service.mapper.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final HouseRepository houseRepository;
    private final PersonMapper personMapper;
    private final HouseMapper houseMapper;

    @Override
    public PersonResponseDTO getByUuid(UUID uuid) {
        PersonEntity personEntity = personRepository.findByUuid(uuid)
                .orElseThrow(() -> EntityNotFoundException.of(PersonResponseDTO.class, uuid));

        return personMapper.toPersonResponseDTO(personEntity);
    }

    @Override
    public List<PersonResponseDTO> getAll() {
        List<PersonResponseDTO> persons = new ArrayList<>();
        for (PersonEntity person : personRepository.findAll()) {
            persons.add(personMapper.toPersonResponseDTO(person));
        }
        return persons;
    }

    @Override
    @Transactional
    public void create(PersonRequestDTO dto) {
        PersonEntity savedPerson = personMapper.toPersonEntity(dto);
        HouseEntity house = houseRepository.findByUuid(dto.houseUuid())
                .orElseThrow(() -> EntityNotFoundException.of(HouseEntity.class, dto.houseUuid()));
        savedPerson.setHouse(house);
        personRepository.save(savedPerson);
    }

    @Override
    @Transactional
    public void update(UUID uuid, PersonRequestDTO dto) {
        PersonEntity updatedPerson = personRepository.findByUuid(uuid)
                .orElseThrow(() -> EntityNotFoundException.of(PersonEntity.class, uuid));
        HouseEntity house = houseRepository.findByUuid(dto.houseUuid())
                .orElseThrow(() -> EntityNotFoundException.of(HouseEntity.class, dto.houseUuid()));
        updatedPerson = personMapper.update(updatedPerson, dto);
        updatedPerson.setHouse(house);

        personRepository.update(updatedPerson);
    }

    @Transactional
    public void patch(UUID uuid, PersonRequestDTO dto) {
        PersonEntity updatedPerson = personRepository.findByUuid(uuid)
                .orElseThrow(() -> EntityNotFoundException.of(PersonEntity.class, uuid));

        updatedPerson = personMapper.merge(updatedPerson, dto);
        UUID houseUuid = dto.houseUuid();
        if(houseUuid != null) {
            HouseEntity house = houseRepository.findByUuid(dto.houseUuid())
                    .orElseThrow(() -> EntityNotFoundException.of(HouseEntity.class, houseUuid));
            updatedPerson.setHouse(house);
        }
        personRepository.update(updatedPerson);
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {
        personRepository.delete(uuid);
    }



    @Override
    public List<PersonResponseDTO> getTenantsByHouseUuid(UUID uuid) {
        List<PersonResponseDTO> tenants = new ArrayList<>();
        HouseEntity house = houseRepository.findByUuid(uuid)
                .orElseThrow(() -> EntityNotFoundException.of(HouseEntity.class, uuid));
        for (PersonEntity tenant : house.getTenants()) {
            tenants.add(personMapper.toPersonResponseDTO(tenant));
        }
        return tenants;
    }

}
