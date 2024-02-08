package by.stolybko.database.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;


public record HouseRequestDTO(
        String area,

        @NotBlank
        String country,

        @NotBlank
        String city,

        @NotBlank
        String street,

        @NotBlank
        String number,
        List<UUID> ownersUuid) {

}
