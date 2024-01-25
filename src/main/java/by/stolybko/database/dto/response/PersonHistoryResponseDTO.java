package by.stolybko.database.dto.response;

import by.stolybko.database.entity.Passport;
import by.stolybko.database.entity.enam.Sex;
import by.stolybko.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;


public record PersonHistoryResponseDTO(
        UUID uuid,
        String name,
        String surname,
        Sex sex,
        Passport passport,

        @JsonFormat(pattern = Constants.TIME_FORMAT_RESPONSE)
        LocalDateTime createDate,

        @JsonFormat(pattern = Constants.TIME_FORMAT_RESPONSE)
        LocalDateTime updateDate,

        @JsonFormat(pattern = Constants.TIME_FORMAT_RESPONSE)
        LocalDateTime exitDate) {

}
