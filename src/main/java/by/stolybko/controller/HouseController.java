package by.stolybko.controller;

import by.stolybko.database.dto.HouseRequestDTO;
import by.stolybko.database.dto.HouseResponseDTO;
import by.stolybko.service.HouseService;
import by.stolybko.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/house")
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;
    private final PersonService personService;

    @GetMapping()
    public ResponseEntity<List<HouseResponseDTO>> getAllHouses() {
        List<HouseResponseDTO> houseResponseDTOList = houseService.getAll();
        return ResponseEntity.ok().body(houseResponseDTOList);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<HouseResponseDTO> getByUuidHouse(@PathVariable(name = "uuid") UUID uuid) {
        HouseResponseDTO houseResponseDTO = houseService.getByUuid(uuid);
        return ResponseEntity.ok().body(houseResponseDTO);
    }

    @GetMapping("/owner/{uuid}")
    public ResponseEntity<List<HouseResponseDTO>> getOwnershipByPersonUuid(@PathVariable(name = "uuid") UUID uuid) {
        List<HouseResponseDTO> ownership = personService.getOwnershipByPersonUuid(uuid);
        return ResponseEntity.ok().body(ownership);
    }

    @PostMapping
    public ResponseEntity<Void> createHouse(@RequestBody HouseRequestDTO houseRequestDTO) {
        houseService.create(houseRequestDTO);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Void> updateHouse(@PathVariable(name = "uuid") UUID uuid, @RequestBody HouseRequestDTO houseRequestDTO) {
        houseService.update(uuid, houseRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteHouse(@PathVariable(name = "uuid") UUID uuid) {
        houseService.delete(uuid);
        return ResponseEntity.ok().build();
    }

}
