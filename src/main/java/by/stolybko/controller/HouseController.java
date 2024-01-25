package by.stolybko.controller;

import by.stolybko.database.dto.PageResponse;
import by.stolybko.database.dto.request.HouseRequestDTO;
import by.stolybko.database.dto.response.HouseResponseDTO;
import by.stolybko.service.HouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/houses")
@RequiredArgsConstructor
@Tag(name = "House controller", description = "The House API")
public class HouseController {

    private final HouseService houseService;

    @GetMapping()
    @Operation(summary = "Возвращает все House постранично, " +
            "для указания страницы и колличества записей задаются ключи page и size")
    public PageResponse<HouseResponseDTO> getAllHouses(Pageable pageable) {
        Page<HouseResponseDTO> houseResponseDTOPage = houseService.getAll(pageable);
        return PageResponse.of(houseResponseDTOPage);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Возвращает House по UUID")
    public ResponseEntity<HouseResponseDTO> getByUuidHouse(@PathVariable UUID uuid) {
        HouseResponseDTO houseResponseDTO = houseService.getByUuid(uuid);
        return ResponseEntity.ok().body(houseResponseDTO);
    }

    @GetMapping("/owner/{uuid}")
    @Operation(summary = "Возвращает все дома которыми владеет Person указанный в UUID")
    public ResponseEntity<List<HouseResponseDTO>> getOwnershipByPersonUuid(@PathVariable UUID uuid) {
        List<HouseResponseDTO> ownership = houseService.getOwnershipByPersonUuid(uuid);
        return ResponseEntity.ok().body(ownership);
    }

    @PostMapping
    @Operation(summary = "Создает новый дом")
    public ResponseEntity<Void> createHouse(@RequestBody @Valid HouseRequestDTO houseRequestDTO) {
        houseService.create(houseRequestDTO);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{uuid}")
    @Operation(summary = "Обновляет дом по указанному UUID")
    public ResponseEntity<Void> updateHouse(@PathVariable UUID uuid, @RequestBody @Valid HouseRequestDTO houseRequestDTO) {
        houseService.update(uuid, houseRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Удаляет дом с указанным UUID")
    public ResponseEntity<Void> deleteHouse(@PathVariable UUID uuid) {
        houseService.delete(uuid);
        return ResponseEntity.ok().build();
    }

}
