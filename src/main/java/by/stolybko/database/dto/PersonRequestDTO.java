package by.stolybko.database.dto;

import by.stolybko.database.entity.Passport;
import by.stolybko.database.entity.enam.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequestDTO {

    private String name;
    private String surname;
    private Sex sex;
    private Passport passport;
    private UUID houseUuid;

}
