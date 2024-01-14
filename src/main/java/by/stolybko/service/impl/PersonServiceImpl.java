package by.stolybko.service.impl;

import by.stolybko.database.dto.HouseResponseDTO;
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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
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
    //@Transactional
    public void create(PersonRequestDTO dto) {
        PersonEntity savedPerson = personMapper.toPersonEntity(dto);
        HouseEntity house = houseRepository.findByUuid(dto.getHouseUuid()).orElseThrow();
        savedPerson.setHouse(house);
        personRepository.save(savedPerson);
    }

    @Override
    //@Transactional
    public void update(UUID uuid, PersonRequestDTO dto) {
        PersonEntity updatedPerson = personRepository.findByUuid(uuid).orElseThrow();
        HouseEntity house = houseRepository.findByUuid(dto.getHouseUuid()).orElseThrow();
        updatedPerson.setName(dto.getName());
        updatedPerson.setSurname(dto.getSurname());
        updatedPerson.setSex(dto.getSex());
        updatedPerson.setPassport(dto.getPassport());
        updatedPerson.setHouse(house);

        personRepository.save(updatedPerson);
    }

    @Override
    //@Transactional
    public void delete(UUID uuid) {
        personRepository.delete(uuid);
    }

    public List<HouseResponseDTO> getOwnershipByPersonUuid(UUID uuid) {
        List<HouseResponseDTO> ownership = new ArrayList<>();
        PersonEntity owner = personRepository.findByUuid(uuid).orElseThrow();
        for (HouseEntity house : owner.getOwnership()) {
            ownership.add(houseMapper.toHouseResponseDTO(house));
        }
        return ownership;
    }

}
