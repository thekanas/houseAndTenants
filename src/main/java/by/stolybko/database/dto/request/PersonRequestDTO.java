package by.stolybko.database.dto.request;

import by.stolybko.database.entity.Passport;
import by.stolybko.database.entity.enam.Sex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;


public record PersonRequestDTO(
        @NotBlank
        @Size(min = 2, max = 40)
        String name,

        @NotBlank
        @Size(min = 2, max = 40)
        String surname,

        @NotNull
        Sex sex,

        @NotNull
        Passport passport,

        @NotNull
        UUID houseUuid) {

}
