package by.stolybko.service;

import by.stolybko.database.dto.HouseResponseDTO;
import by.stolybko.database.dto.PersonRequestDTO;
import by.stolybko.database.dto.PersonResponseDTO;

import java.util.List;
import java.util.UUID;


public interface PersonService extends CRUDService<PersonResponseDTO, PersonRequestDTO> {

    List<HouseResponseDTO> getOwnershipByPersonUuid(UUID uuid);

}
