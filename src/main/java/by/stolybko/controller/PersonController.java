package by.stolybko.controller;

import by.stolybko.database.dto.PersonRequestDTO;
import by.stolybko.database.dto.PersonResponseDTO;
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
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;
    private final HouseService houseService;

    @GetMapping()
    public ResponseEntity<List<PersonResponseDTO>> getAllPersons() {
        List<PersonResponseDTO> personResponseDTOList = personService.getAll();
        return ResponseEntity.ok().body(personResponseDTOList);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<PersonResponseDTO> getByUuidPerson(@PathVariable(name = "uuid") UUID uuid) {
        PersonResponseDTO personResponseDTO = personService.getByUuid(uuid);
        return ResponseEntity.ok().body(personResponseDTO);
    }

    @GetMapping("/house/{uuid}")
    public ResponseEntity<List<PersonResponseDTO>> getTenantsByHouseUuid(@PathVariable(name = "uuid") UUID uuid) {
        List<PersonResponseDTO> personResponseDTOList = houseService.getTenantsByHouseUuid(uuid);
        return ResponseEntity.ok().body(personResponseDTOList);
    }

    @PostMapping
    public ResponseEntity<Void> createPerson(@RequestBody PersonRequestDTO personRequestDTO) {
        personService.create(personRequestDTO);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Void> updatePerson(@PathVariable(name = "uuid") UUID uuid, @RequestBody PersonRequestDTO personRequestDTO) {
        personService.update(uuid, personRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deletePerson(@PathVariable(name = "uuid") UUID uuid) {
        personService.delete(uuid);
        return ResponseEntity.ok().build();
    }

}
