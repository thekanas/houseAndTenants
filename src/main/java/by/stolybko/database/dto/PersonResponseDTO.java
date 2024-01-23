package by.stolybko.database.dto;

import by.stolybko.database.entity.Passport;
import by.stolybko.database.entity.enam.Sex;
import by.stolybko.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class PersonResponseDTO {

    UUID uuid;
    String name;
    String surname;
    Sex sex;
    Passport passport;

    @JsonFormat(pattern = Constants.TIME_FORMAT_RESPONSE)
    LocalDateTime createDate;

    @JsonFormat(pattern = Constants.TIME_FORMAT_RESPONSE)
    LocalDateTime updateDate;

}


