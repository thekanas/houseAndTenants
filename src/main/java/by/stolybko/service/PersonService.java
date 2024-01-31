package by.stolybko.service;

import by.stolybko.database.dto.request.PersonRequestDTO;
import by.stolybko.database.dto.response.PersonResponseDTO;

import java.util.List;
import java.util.UUID;


public interface PersonService extends CRUDService<PersonResponseDTO, PersonRequestDTO> {

    List<PersonResponseDTO> getTenantsByHouseUuid(UUID uuid);

}
