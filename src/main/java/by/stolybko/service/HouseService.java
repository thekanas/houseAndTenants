package by.stolybko.service;

import by.stolybko.database.dto.request.HouseRequestDTO;
import by.stolybko.database.dto.response.HouseResponseDTO;

import java.util.List;
import java.util.UUID;

public interface HouseService extends CRUDService<HouseResponseDTO, HouseRequestDTO>{


    List<HouseResponseDTO> getOwnershipByPersonUuid(UUID uuid);

}
