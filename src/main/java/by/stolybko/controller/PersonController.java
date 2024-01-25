package by.stolybko.controller;

import by.stolybko.database.dto.PageResponse;
import by.stolybko.database.dto.request.PersonRequestDTO;
import by.stolybko.database.dto.response.PersonResponseDTO;
import by.stolybko.service.PersonService;

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
@RequestMapping("/persons")
@RequiredArgsConstructor
@Tag(name = "Person controller", description = "The Person API")
public class PersonController {

    private final PersonService personService;

    @GetMapping()
    @Operation(summary = "Возвращает всех Person постранично, " +
            "для указания страницы и колличества записей задаются ключи page и size")
    public PageResponse<PersonResponseDTO> getAllPersons(Pageable pageable) {
        Page<PersonResponseDTO> personResponseDTOPage = personService.getAll(pageable);
        return PageResponse.of(personResponseDTOPage);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Возвращает Person по UUID")
    public ResponseEntity<PersonResponseDTO> getByUuidPerson(@PathVariable UUID uuid) {
        PersonResponseDTO personResponseDTO = personService.getByUuid(uuid);
        return ResponseEntity.ok().body(personResponseDTO);
    }

    @GetMapping("/house/{uuid}")
    @Operation(summary = "Возвращает всех жильцов дома по UUID дома")
    public ResponseEntity<List<PersonResponseDTO>> getTenantsByHouseUuid(@PathVariable UUID uuid) {
        List<PersonResponseDTO> personResponseDTOList = personService.getTenantsByHouseUuid(uuid);
        return ResponseEntity.ok().body(personResponseDTOList);
    }

    @PostMapping
    @Operation(summary = "Создаёт нового Person")
    public ResponseEntity<Void> createPerson(@RequestBody @Valid PersonRequestDTO personRequestDTO) {
        personService.create(personRequestDTO);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{uuid}")
    @Operation(summary = "Обновляет Person по указанному UUID")
    public ResponseEntity<Void> updatePerson(@PathVariable UUID uuid, @RequestBody @Valid PersonRequestDTO personRequestDTO) {
        personService.update(uuid, personRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Удаляет Person по указанному UUID")
    public ResponseEntity<Void> deletePerson(@PathVariable UUID uuid) {
        personService.delete(uuid);
        return ResponseEntity.ok().build();
    }

}
