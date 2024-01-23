package by.stolybko.database.dto;

import by.stolybko.database.entity.Passport;
import by.stolybko.database.entity.enam.Sex;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;


public record PersonRequestDTO(
        @NotBlank(message = "Person name is blank.")
        @Size(min = 2, max = 40)
        String name,

        @NotBlank(message = "Person surname is blank.")
        @Size(min = 2, max = 40)
        String surname,
        Sex sex,
        Passport passport,
        UUID houseUuid) {

}
