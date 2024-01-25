package by.stolybko.controller;

import by.stolybko.database.dto.response.HouseHistoryResponseDTO;
import by.stolybko.database.dto.response.PersonHistoryResponseDTO;
import by.stolybko.service.impl.HouseHistoryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
@Tag(name = "House History controller", description = "The House History API")
public class HouseHistoryController {

    private final HouseHistoryServiceImpl houseHistoryService;

    @GetMapping("/tenants/house/{houseUuid}")
    @Operation(summary = "Возвращает всех Person ранее проживавших в доме с указанным UUID")
    public ResponseEntity<List<PersonHistoryResponseDTO>> getPersonTenantHistoryByHouseUuid(@PathVariable UUID houseUuid) {
        List<PersonHistoryResponseDTO> personHistoryResponseDTOList = houseHistoryService.getPersonTenantHistoryByHouseUuid(houseUuid);
        return ResponseEntity.ok().body(personHistoryResponseDTOList);
    }

    @GetMapping("/owners/house/{houseUuid}")
    @Operation(summary = "Возвращает всех Person ранее владевших домом с указанным UUID")
    public ResponseEntity<List<PersonHistoryResponseDTO>> getPersonOwnerHistoryByHouseUuid(@PathVariable UUID houseUuid) {
        List<PersonHistoryResponseDTO> personHistoryResponseDTOList = houseHistoryService.getPersonOwnerHistoryByHouseUuid(houseUuid);
        return ResponseEntity.ok().body(personHistoryResponseDTOList);
    }

    @GetMapping("/houses/tenant/{tenantUuid}")
    @Operation(summary = "Возвращает все House в которых ранее проживал Person с указанным UUID")
    public ResponseEntity<List<HouseHistoryResponseDTO>> getHousesHistoryByPersonTenantUuid(@PathVariable UUID tenantUuid) {
        List<HouseHistoryResponseDTO> houseHistoryResponseDTOList = houseHistoryService.getHousesHistoryByPersonTenantUuid(tenantUuid);
        return ResponseEntity.ok().body(houseHistoryResponseDTOList);
    }

    @GetMapping("/houses/owner/{ownerUuid}")
    @Operation(summary = "Возвращает все House которыми ранее владел Person с указанным UUID")
    public ResponseEntity<List<HouseHistoryResponseDTO>> getHousesHistoryByPersonOwnerUuid(@PathVariable UUID ownerUuid) {
        List<HouseHistoryResponseDTO> houseHistoryResponseDTOList = houseHistoryService.getHousesHistoryByPersonOwnerUuid(ownerUuid);
        return ResponseEntity.ok().body(houseHistoryResponseDTOList);
    }

}
