package by.stolybko.controller;

import by.stolybko.database.dto.HouseRequestDTO;
import by.stolybko.database.dto.HouseResponseDTO;
import by.stolybko.service.HouseService;
import lombok.RequiredArgsConstructor;
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
public class HouseController {

    private final HouseService houseService;

    @GetMapping()
    public ResponseEntity<List<HouseResponseDTO>> getAllHouses() {
        List<HouseResponseDTO> houseResponseDTOList = houseService.getAll();
        return ResponseEntity.ok().body(houseResponseDTOList);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<HouseResponseDTO> getByUuidHouse(@PathVariable UUID uuid) {
        HouseResponseDTO houseResponseDTO = houseService.getByUuid(uuid);
        return ResponseEntity.ok().body(houseResponseDTO);
    }

    @GetMapping("/owner/{uuid}")
    public ResponseEntity<List<HouseResponseDTO>> getOwnershipByPersonUuid(@PathVariable UUID uuid) {
        List<HouseResponseDTO> ownership = houseService.getOwnershipByPersonUuid(uuid);
        return ResponseEntity.ok().body(ownership);
    }

    @PostMapping
    public ResponseEntity<Void> createHouse(@RequestBody HouseRequestDTO houseRequestDTO) {
        houseService.create(houseRequestDTO);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Void> updateHouse(@PathVariable UUID uuid, @RequestBody HouseRequestDTO houseRequestDTO) {
        houseService.update(uuid, houseRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<Void> patchHouse(@PathVariable UUID uuid, @RequestBody HouseRequestDTO houseRequestDTO) {
        houseService.patch(uuid, houseRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteHouse(@PathVariable UUID uuid) {
        houseService.delete(uuid);
        return ResponseEntity.ok().build();
    }

}
