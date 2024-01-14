package by.stolybko.database.dto;

import by.stolybko.database.entity.Passport;
import by.stolybko.database.entity.enam.Sex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequestDTO {

    @Size(min = 2, max = 40, message = "Name should be between 2 and 40 characters")
    private String name;

    @Size(min = 2, max = 40, message = "Surname should be between 2 and 40 characters")
    private String surname;
    private Sex sex;
    private Passport passport;
    private UUID houseUuid;

}
