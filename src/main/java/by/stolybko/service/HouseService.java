package by.stolybko.service;

import by.stolybko.database.dto.HouseRequestDTO;
import by.stolybko.database.dto.HouseResponseDTO;
import by.stolybko.database.dto.PersonResponseDTO;

import java.util.List;
import java.util.UUID;

public interface HouseService extends CRUDService<HouseResponseDTO, HouseRequestDTO>{

    List<PersonResponseDTO> getTenantsByHouseUuid(UUID uuid);

}
