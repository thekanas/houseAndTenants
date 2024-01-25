package by.stolybko.service;

import by.stolybko.database.dto.response.HouseHistoryResponseDTO;
import by.stolybko.database.dto.response.PersonHistoryResponseDTO;

import java.util.List;
import java.util.UUID;

public interface HouseHistoryService {

    List<PersonHistoryResponseDTO> getPersonTenantHistoryByHouseUuid(UUID houseUuid);
    List<PersonHistoryResponseDTO> getPersonOwnerHistoryByHouseUuid(UUID houseUuid);
    List<HouseHistoryResponseDTO> getHousesHistoryByPersonTenantUuid(UUID tenantUuid);
    List<HouseHistoryResponseDTO> getHousesHistoryByPersonOwnerUuid(UUID tenantUuid);

}
